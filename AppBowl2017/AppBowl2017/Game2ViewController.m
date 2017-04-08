//
//  Game2ViewController.m
//  AppBowl2017
//
//  Created by Ethan Tillison on 21/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import "Game2ViewController.h"

@interface Game2ViewController ()

@end

@implementation Game2ViewController

@synthesize toDrop, numberToDrop, buttons, rules;

float size;
BOOL dropping = NO;

#pragma mark - ViewController methods

- (void)viewDidLoad {
	[super viewDidLoad];
	// Do any additional setup after loading the view.
}

- (void)viewWillAppear:(BOOL)animated { // Just before the view appears...
	[super viewWillAppear:animated];
	
	size = self.view.frame.size.width / 5; // Set the tile size to one fifth of the screen width
	
	// Initialize the tile arrays
	self.tileArray = [[NSMutableArray alloc] initWithCapacity:30];
	self.tileLabels = [[NSMutableArray alloc] initWithCapacity:30];
	for (int n = 0; n < 30; n++) {
		self.tileArray[n] = @0; // Fill the arrays with empty tiles
		self.tileLabels[n] = @0;
	}
	
	self.buttons = [[NSMutableArray alloc] initWithCapacity:5]; // Initialize the button array
	for (int i = 0; i < 5; i++) { // Initialize each button
		UIButton *button = [UIButton buttonWithType:UIButtonTypeSystem];
		[button setFrame:[self frameForTileAtX:i Y:0 withOffset:0]];
		[self.buttons setObject:button atIndexedSubscript:i]; // Add it to the array
		[self.view addSubview:button]; // Add it to the view
		[button addTarget:self action:@selector(buttonPressed:) forControlEvents:UIControlEventTouchUpInside]; // Trigger buttonPressed when the button is pressed
		[self startAlphaFlash:button];
		[button.titleLabel setFont:[UIFont fontWithName:@"Montserrat" size:48]];
		[button setBackgroundColor:[UIColor lightGrayColor]];
	}
	
	[self prepareToDrop]; // Get the first tile ready to drop
	
	// Add 2 rules to the rule array
	rules = [@[[[AdjacencyRule alloc] initWithTile:[NSNumber numberWithInt:arc4random() % 9 + 1] and:[NSNumber numberWithInt:arc4random() % 9 + 1]]] mutableCopy];
	[self addNewRule];
}

- (void)didReceiveMemoryWarning {
	[super didReceiveMemoryWarning];
	// Dispose of any resources that can be recreated.
}

#pragma mark - Game methods

/// Animate clearing the board, can execute completion when the animation is finished
- (void)clearWithCompletion:(void (^)(BOOL finished))completion {
	[UIView animateWithDuration:0.5 delay:0 options:UIViewAnimationOptionCurveLinear animations:^{
		for (id cell in self.tileLabels) {
			if ([cell isKindOfClass:[UILabel class]]) {
				[(UILabel *) cell setCenter:CGPointMake([cell center].x, [cell center].y + 6 * size)]; // Move each tile down six spaces (i.e. off the bottom of the board
			}
		}
	} completion:completion];
}

/// Clear the arrays and reset the positions of the initial components.
- (void)reset {
	// Fill arrays with empty tile
	for (int n = 0; n < 30; n++) {
		self.tileArray[n] = @0;

		[self.tileLabels[n] removeFromSuperview]; // Remove existing tiles from view
		self.tileLabels[n] = @0;
	}

	for (int i = 0; i < 5; i++) { // Reset the frames of the buttons
		[(UIButton *) buttons[i] setFrame:[self frameForTileAtX:i Y:0 withOffset:0]];
	}

	if (self.toDrop) {
		[self.toDrop removeFromSuperview]; // This will be recreated with prepareToDrop
	}
	
	[UIView animateWithDuration:0.25 animations:^{
		[self.levelComplete setAlpha:0]; // Remove the level complete banner
	} completion:^(BOOL finished) {
		[self.levelComplete removeFromSuperview];
	}];
	
	[self prepareToDrop];
}

/// Animate dropping the given UIView to the given x and y coordinates, update the given button, check if the player has won, and prepare the next tile
- (void)dropTile:(UIView *)tile toX:(NSUInteger)x Y:(NSUInteger)y andReplaceButton:(UIButton *)button prepare:(BOOL)prepare {
	[UIView animateWithDuration:0.05 + 0.1 * abs(2 - (int) x) delay:0 options:UIViewAnimationOptionCurveLinear animations:^{
		tile.center = [self centerPointForTileAtX:x Y:6]; // Move the tile to the appropriate column
	} completion:^(BOOL finished) {
		[UIView animateWithDuration:0.1 * abs(6 - (int) y) delay:0 options:UIViewAnimationOptionCurveLinear animations:^{
			tile.center = [self centerPointForTileAtX:x Y:y]; // Drop the tile to the appropriate spot
		} completion:^(BOOL finished) {
			if (button) { // If a button has been provided
				[UIView animateWithDuration:0.00001 animations:^{ // Hide it (animate has been used to stop the flash animation)
					[button setAlpha:0];
				}];
				if (y >= 5) { // If the button would be moved above the top of the field...
					[button setEnabled:false]; // Disable it
				}
			}
			
			// Test for rule violations, and destoy the cells
			[self destroyAndDropAroundCellsInSet:[self setOfInvalidValuesFromMovedCells:[NSSet setWithObject:[NSNumber numberWithInteger:x + 5 * y]]] completion:^(BOOL finished) {
				BOOL fail = NO; // YES if the player has not won
				for (int i = 0; i < 30; i++) {
					if ([self.tileArray[i] intValue] <= 0) { // If there is an empty cell...
						fail = YES; // The player has not won
						break;
					}
				}
				if (!fail) { // If the player has won
					// Create a level complete banner
					self.levelComplete = [[UILabel alloc] initWithFrame:CGRectMake(0, [UIApplication sharedApplication].statusBarFrame.size.height + self.navigationController.navigationBar.frame.size.height, self.view.frame.size.width, 128)];
					[self.levelComplete setTextAlignment:NSTextAlignmentCenter]; // Align the text in the center
					[self.levelComplete setContentMode:UIViewContentModeTop]; // ... and at the top
					[self.levelComplete setText:@"Level Complete!"]; // Set the text
					[self.levelComplete setFont:[UIFont fontWithName:@"Montserrat" size:64]]; // Set the font size
					[self.levelComplete setMinimumScaleFactor:0.1]; // Allow the text to shrink to fit the screen
					[self.levelComplete setAdjustsFontSizeToFitWidth:YES];
					[self.levelComplete setTextColor:[UIColor colorWithRed:0 green:0.5 blue:0 alpha:1]]; // Set the text to dark green
					[self.levelComplete setAlpha:0]; // Start at alpha = 0 to add a fade in animation
					[self.view addSubview:self.levelComplete]; // Add it to the view
					dropping = NO; // We are no longer dropping a tile
					
					[UIView animateWithDuration:0.5 animations:^{
						[self.levelComplete setAlpha:1]; // Fade the banner in
					} completion:^(BOOL finished) {
						[self clearWithCompletion:^(BOOL finished) { // Clear the board
							// Add a new rule
							[self addNewRule];
							[self.level setText:[NSString stringWithFormat:@"Level %i", (int) self.rules.count - 1]]; // Update the level display
							[self performSelector:@selector(reset) withObject:nil afterDelay:1]; // Reset the board after 1 second
						}];
					}];
					return;
				}
				if (prepare) {
					[self prepareToDrop];
					dropping = NO; // The tile is no longer falling
				}
			}];
		}];
	}];
}

/// A unique set of cells that currently violate rules
- (NSSet *)setOfInvalidValuesFromMovedCells:(NSSet *)cells {
	NSMutableSet *ret = [[NSMutableSet alloc] init];
	
	for (id cell in cells) {
		if (![cell isKindOfClass:[NSNumber class]]) continue; // If the cell is not a number (to prevent errors)
		int c = [cell intValue];
		int x = c % 5; // X and y can be calculated based on the width (5)
		int y = c / 5;
		NSNumber *v = [self.tileArray objectAtIndex:c]; // The value of the tile at the cell
		for (Rule *rule in rules) { // Check each adjacent tile for each rule
			if (x > 0 && ![rule isNewTile:v allowedNextTo:[self.tileArray objectAtIndex:c - 1] inTileArray:self.tileArray]) {
				[ret addObject:cell];
				[ret addObject:[NSNumber numberWithInt:c - 1]];
			}
			
			if (x < 4 && ![rule isNewTile:v allowedNextTo:[self.tileArray objectAtIndex:c + 1] inTileArray:self.tileArray]) {
				[ret addObject:cell];
				[ret addObject:[NSNumber numberWithInt:c + 1]];
			}
			
			if (y > 0 && ![rule isNewTile:v allowedNextTo:[self.tileArray objectAtIndex:c - 5] inTileArray:self.tileArray]) {
				[ret addObject:cell];
				[ret addObject:[NSNumber numberWithInt:c - 5]];
			}
			
			if (y < 5 && ![rule isNewTile:v allowedNextTo:[self.tileArray objectAtIndex:c + 5] inTileArray:self.tileArray]) {
				[ret addObject:cell];
				[ret addObject:[NSNumber numberWithInt:c + 5]];
			}
		}
	}
	
	return ret;
}


/// Animate the destruction of all of the adjacent cells to those in the set, and execute completion when it is finished
- (void)destroyAndDropAroundCellsInSet:(NSSet *)set completion:(void (^)(BOOL finished))completion {
	if (set.count == 0) { // If the set is empty, the method is complete; run the completion block and return
		if (completion) completion(YES);
		return;
	}

	[self processViolatingSet:set completion:^(BOOL finished, NSSet *toDestroy) { // Turn the violating cells red
		[self animateDestroyingCells:toDestroy completion:^(BOOL finished) { // Destroy all affected cells
			[self animateFallingCellsWithCompletion:completion];
		}];
	}];
}

/// Turn all cells in the violating set red, and perform a completion method on the set of cells including the violating set, plus the adjacent squares
- (void)processViolatingSet:(NSSet *)violating completion:(void (^)(BOOL finished, NSSet *toDestroy))completion {
	NSMutableSet *ret = [[NSMutableSet alloc] initWithSet:violating]; // Create a return set that includes the vilating set
	
	BOOL b = YES; // A boolean variable to ensure the completion block is run only the first time.
	for (id cell in violating) { // Iterate through all of the cells in the give set.
		if (![cell isKindOfClass:[NSNumber class]]) continue; // Check the class to ensure no errors
		
		int c = [cell intValue];
		int x = c % 5; // X and Y can be determined based on the width (5)
		int y = c / 5;

		// Destroy the four cells surrounding the violating cell
		if (x > 0) [ret addObject:[NSNumber numberWithInt:c - 1]];
		if (x < 4) [ret addObject:[NSNumber numberWithInt:c + 1]];
		if (y > 0) [ret addObject:[NSNumber numberWithInt:c - 5]];
		if (y < 5) [ret addObject:[NSNumber numberWithInt:c + 5]];
		
		// Set the cells' color to red to indicate a violation
		[UIView transitionWithView:[self.tileLabels objectAtIndex:[cell integerValue]] duration:0.5 options:UIViewAnimationOptionTransitionCrossDissolve animations:^{
			[(UILabel *) [self.tileLabels objectAtIndex:[cell integerValue]] setBackgroundColor:[UIColor redColor]];
		} completion:b ? ^(BOOL finished) {
			completion(finished, ret);
		}: nil];
		b = NO; // This is no longer the first execution
	}
}

/// Animate the (shrinking) destruction of the given cells
- (void)animateDestroyingCells:(NSSet *)cellsToDestroy completion:(void (^)(BOOL finished))completion {
	[UIView animateWithDuration:0.5 animations:^{
		for (id k in cellsToDestroy) {
			if (![k isKindOfClass:[NSNumber class]]) continue;
			
			// Remove the cell from the numerical array
			self.tileArray[[k integerValue]] = @0;
			
			// Shrink (destroy) all of the affected cells
			id label = [self.tileLabels objectAtIndex:[k integerValue]];
			if ([label isKindOfClass:[UILabel class]])
				[(UILabel *) label setTransform:CGAffineTransformScale(CGAffineTransformIdentity, 0.00001, 0.00001)];
		}
	} completion:completion];
}


/// Animate all cells that need to fall falling
- (void)animateFallingCellsWithCompletion:(void (^)(BOOL finished))completion {
	NSMutableSet *moved = [[NSMutableSet alloc] init]; // A set to store the tiles that have moved and can therefore
													   // Be potential candidates for an additional rule violation

	// Have all floating tiles fall
	[UIView animateWithDuration:0.25 delay:0 options:UIViewAnimationOptionCurveLinear animations:^{
		for (int x = 0; x < 5; x++) { // Iterate through each tile to test if it can fall
			for (int y = 0; y < 6; y++) {
				int newY = [self lowestSpaceInColumn:x]; // Find the Y value to fall to
				if (newY > y) continue; // Don't drop tiles that cannot be dropped further

				self.tileArray[x + 5 * newY] = self.tileArray[x + 5 * y]; // Drop the tile
				self.tileArray[x + 5 * y] = @0; // Remove the original tile

				// Repeat for the labels
				self.tileLabels[x + 5 * newY] = self.tileLabels[x + 5 * y];
				self.tileLabels[x + 5 * y] = @0;

				// The tile moved; add it to the set
				[moved addObject:[NSNumber numberWithInt:x + 5 * newY]];

				if ([self.tileLabels[x + 5 * newY] isKindOfClass:[UILabel class]]) { // If the label is actually a label...
					[UIView animateWithDuration:0.25 delay:0 options:UIViewAnimationOptionCurveLinear animations:^{ // Update its position
						[[self.tileLabels objectAtIndex:x + 5 * newY] setFrame:[self frameForTileAtX:x Y:newY withOffset:0]];
					} completion:nil];
				}
			}
		}
	} completion:^(BOOL finished) { // When all tiles have finished falling...
		// Check for new violations and repeat the process
		[self destroyAndDropAroundCellsInSet:[self setOfInvalidValuesFromMovedCells:moved] completion:completion];
	}];
}

/// Finds the lowest empty space in the given column
- (int)lowestSpaceInColumn:(NSUInteger)col {
	for (int y = 0; y < 6; y++) { // Iterate from the bottom
		if ([(NSNumber *) self.tileArray[col + 5 * y] intValue] < 1) // If the cell is blank...
			return y; // Return its y location
	}
	return INT_MAX; // If there are no blank cells, return a large number instead.
}

/// Called when a button is pressed
- (void)buttonPressed:(UIButton *)sender {
	if (dropping) return; // If there is already a falling cell, do nothing
	dropping = YES; // There is now a falling cell
	
	int x = (int) (sender.center.x / size); // Get the x coordinate from the button's position
	int y = [self lowestSpaceInColumn:x]; // The tile will fall to the lowest open space in the column.
	
	self.tileArray[x + 5 * y] = [NSNumber numberWithInteger:numberToDrop]; // Set the value in the tile array
	self.tileLabels[x + 5 * y] = toDrop; // Add the label to the label array
	
	[self dropTile:toDrop toX:x Y:y andReplaceButton:sender prepare:YES]; // Animate the tile falling
}

/// Create a new cell to drop and update the position of the buttons
- (void)prepareToDrop {
	numberToDrop = 1 + arc4random() % 9; // Generate a new tile number
	
	// Create the label of the tile to drop
	toDrop = [[UILabel alloc] initWithFrame:[self frameForTileAtX:2 Y:6 withOffset:-16]]; // Set the position above the board
	[toDrop setText:[NSString stringWithFormat:@"%li", (long) numberToDrop]]; // Set the text
	[toDrop setFont:[UIFont fontWithName:@"Montserrat" size:48]]; // Set the font
	[toDrop setTextAlignment:NSTextAlignmentCenter]; // Align the text in the center
	[toDrop setBackgroundColor:[UIColor lightGrayColor]]; // Color the tile light gray
	toDrop.alpha = 0; // Set the alpha to 0 to fade it in later
	[self.view addSubview:toDrop]; // Add the new label to the view.
	[UIView animateWithDuration:0.25 animations:^{
		toDrop.alpha = 1; // Fade in
	}];
	
	for (int i = 0; i < 5; i++) { // Change the titles of the buttons to the new tile number
		UIButton *button = (UIButton *) [buttons objectAtIndex:i];
		[UIView transitionWithView:button duration:0.25 options:UIViewAnimationOptionTransitionCrossDissolve animations:^{
			[button setTitle:[NSString stringWithFormat:@"%li", (long) numberToDrop] forState:UIControlStateNormal];
		} completion:^(BOOL finished) {
			[self startAlphaFlash:button]; // The animation stops the alpha animation; restart it
		}];
	}
	
	[UIView animateWithDuration:0.25 delay:0 options:UIViewAnimationOptionCurveLinear animations:^{
		for (int x = 0; x < 5; x++) { // Raise the buttons to where the tile would be dropped to.
			int n = [self lowestSpaceInColumn:x];
			[(UIButton *) self.buttons[x] setEnabled:(n < 255)]; // If there is no space, disable the button...
			[(UIButton *) self.buttons[x] setAlpha:(n < 255 ? 0.5 : 0)]; // ... and hide it from the view
			[self.buttons[x] setFrame:[self frameForTileAtX:x Y:n withOffset:0]]; // Move to the appropriate space
		}
	} completion:nil];
}

- (void)addNewRule {
	Rule *newRule = [[AdjacencyRule alloc] initWithTile:[NSNumber numberWithInt:arc4random() % 9 + 1] and:[NSNumber numberWithInt:arc4random() % 9 + 1]];
	for (Rule *rule in self.rules) {
		if ([rule isEqual:newRule]) {
			[self addNewRule];
			return;
		}
	}
	[self.rules addObject:newRule];
	
	[self.ruleCount setText:[NSString stringWithFormat:@"Rules: %i", (int) self.rules.count]];
}

/// Add a repeating animation that changes the alpha of the given button from 0.4 to 0.6 and back
- (void)startAlphaFlash:(UIButton *)button {
	[button setAlpha:0.6];
	[UIView animateWithDuration:1 delay:0 options:UIViewAnimationOptionAutoreverse | UIViewAnimationOptionRepeat | UIViewAnimationOptionAllowUserInteraction animations:^{
		[button setAlpha:0.4];
	} completion:nil];
}

#pragma mark - Positioning convenience methods

/// The center point of the tile at the given location
- (CGPoint)centerPointForTileAtX:(NSUInteger)x Y:(NSUInteger)y {
	return CGPointMake((x + 0.5) * size, self.view.frame.size.height - (y + 0.5) * size);
}

/// The frame rectangle for the tile at the given location, lowered by a y offset
- (CGRect)frameForTileAtX:(NSUInteger)x Y:(NSUInteger)y withOffset:(CGFloat)offsetY {
	return CGRectMake(x * size + 5, self.view.frame.size.height - (y + 1) * size + 5 + offsetY, size - 10, size - 10);
}

/*
 #pragma mark - Navigation
 
 // In a storyboard-based application, you will often want to do a little preparation before navigation
 - (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
 // Get the new view controller using [segue destinationViewController].
 // Pass the selected object to the new view controller.
 }
 */

@end
