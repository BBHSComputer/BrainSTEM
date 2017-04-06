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

- (void)viewDidLoad {
	[super viewDidLoad];
	// Do any additional setup after loading the view.
}

- (void)viewWillAppear:(BOOL)animated {
	[super viewWillAppear:animated];
	
	size = self.view.frame.size.width / 5;
	
	self.tileArray = [[NSMutableArray alloc] initWithCapacity:30];
	self.tileLabels = [[NSMutableArray alloc] initWithCapacity:30];
	for (int n = 0; n < 30; n++) {
		self.tileArray[n] = @0;
		self.tileLabels[n] = @0;
	}
	
	self.buttons = [[NSMutableArray alloc] initWithCapacity:5];
	for (int i = 0; i < 5; i++) {
		UIButton *button = [UIButton buttonWithType:UIButtonTypeSystem];
		[button setFrame:[self frameForTileAtX:i Y:0 withOffset:0]];
		[self.buttons setObject:button atIndexedSubscript:i];
		[self.view addSubview:button];
		[button addTarget:self action:@selector(buttonPressed:) forControlEvents:UIControlEventTouchUpInside];
		[self startAlphaFlash:button];
		[button.titleLabel setFont:[UIFont fontWithName:@"Montserrat" size:48]];
		[button setBackgroundColor:[UIColor lightGrayColor]];
	}
	
	[self prepareToDropWithButton:nil atX:0 Y:0];
	
	rules = [@[[[AdjacencyRule alloc] initWithTile:[NSNumber numberWithInt:arc4random() % 9 + 1] and:[NSNumber numberWithInt:arc4random() % 9 + 1]]] mutableCopy];
	for (int i = 0; i < 5; i++) {
		[rules addObject:[[AdjacencyRule alloc] initWithTile:[NSNumber numberWithInt:arc4random() % 9 + 1] and:[NSNumber numberWithInt:arc4random() % 9 + 1]]];
	}
}

- (void)didReceiveMemoryWarning {
	[super didReceiveMemoryWarning];
	// Dispose of any resources that can be recreated.
}

- (void)dropTile:(UIView *)tile toX:(NSUInteger)x Y:(NSUInteger)y andReplaceButton:(UIButton *)button prepare:(BOOL)prepare {
	[UIView animateWithDuration:0.05 + 0.1 * abs(2 - (int) x) delay:0 options:UIViewAnimationOptionCurveLinear animations:^{
		tile.center = [self centerPointForTileAtX:x Y:6];
	} completion:^(BOOL finished) {
		[UIView animateWithDuration:0.1 * abs(6 - (int) y) delay:0 options:UIViewAnimationOptionCurveLinear animations:^{
			tile.center = [self centerPointForTileAtX:x Y:y];
		} completion:^(BOOL finished) {
			if (button) {
				[UIView animateWithDuration:0.00001 animations:^{
					[button setAlpha:0];
				}];
				if (y >= 5) {
					[button setEnabled:false];
				}
			}
			
			//			[self validateDropAtX:x Y:y completion:^(BOOL finished) {
			//				BOOL fail = NO;
			//				for (int i = 0; i < 30; i++) {
			//					if ([self.tileArray[i] intValue] <= 0) {
			//						fail = YES;
			//						break;
			//					}
			//				}
			//				if (!fail) {
			//					UILabel *label = [[UILabel alloc] initWithFrame:self.view.frame];
			//					[label setTextAlignment:NSTextAlignmentCenter];
			//					[label setText:@"You Win!"];
			//					[label setFont:[UIFont fontWithName:@"Montserrat" size:64]];
			//					[self.view addSubview:label];
			//				}
			//				if (prepare) {
			//					[self prepareToDropWithButton:y >= 5 ? nil : button atX:x Y:y];
			//					dropping = NO;
			//				}
			//			}];
			
			[self destroyAndDropAroundCellsInSet:[self setOfInvalidValuesFromMovedCells:[NSSet setWithObject:[NSNumber numberWithInteger:x + 5 * y]]] completion:^(BOOL finished) {
				BOOL fail = NO;
				for (int i = 0; i < 30; i++) {
					if ([self.tileArray[i] intValue] <= 0) {
						fail = YES;
						break;
					}
				}
				if (!fail) {
					UILabel *label = [[UILabel alloc] initWithFrame:self.view.frame];
					[label setTextAlignment:NSTextAlignmentCenter];
					[label setText:@"You Win!"];
					[label setFont:[UIFont fontWithName:@"Montserrat" size:64]];
					[self.view addSubview:label];
				}
				if (prepare) {
					[self prepareToDropWithButton:y >= 5 ? nil : button atX:x Y:y];
					dropping = NO;
				}
			}];
		}];
	}];
}

- (void)validateDropAtX:(NSInteger)x Y:(NSInteger)y completion:(void (^)(BOOL finished)) completion {
	// Go through each rule
	for (Rule *rule in rules) {
		// Check each adjacent square (make sure it's not an edge) to see if it violates the rule.
		BOOL l = (x > 0 && ![rule isNewTile:self.tileArray[x + 5 * y] allowedNextTo:self.tileArray[x - 1 + 5 * y] inTileArray:self.tileArray]);
		BOOL r = (x < 4 && ![rule isNewTile:self.tileArray[x + 5 * y] allowedNextTo:self.tileArray[x + 1 + 5 * y] inTileArray:self.tileArray]);
		BOOL d = (y > 0 && ![rule isNewTile:self.tileArray[x + 5 * y] allowedNextTo:self.tileArray[x + 5 * y - 5] inTileArray:self.tileArray]);
		NSLog(@"A");
		
		// BOOL u = (y < 5 && ![r isNewTile:self.tileArray[x + 5 * y] allowedNextTo:self.tileArray[x + 5 * y + 5] inTileArray:self.tileArray]);
		if (l || r || d) {
			UILabel *other;
			if (d) other = self.tileLabels[x + 5 * y - 5];
			if (l) other = self.tileLabels[x - 1 + 5 * y];
			if (r) other = self.tileLabels[x + 1 + 5 * y];
			
			[UIView transitionWithView:other duration:0.5 options:UIViewAnimationOptionTransitionCrossDissolve animations:^{
				[other setBackgroundColor:[UIColor redColor]];
			} completion:nil];
			[UIView transitionWithView:self.tileLabels[x + 5 * y] duration:0.5 options:UIViewAnimationOptionTransitionCrossDissolve animations:^{
				[self.tileLabels[x + 5 * y] setBackgroundColor:[UIColor redColor]];
				NSLog(@"B");
			} completion:^(BOOL finished) {
				[UIView animateWithDuration:0.25 animations:^{
					for (int y2 = -1; y2 <= 1; y2++) { // Go through each tile in a 3x3 square around the dropped tile
						if (y + y2 < 0 || y + y2 > 5) continue; // If the tile is out of bounds (out of the playing field)
						for (int x2 = -1; x2 <= 1; x2++) {
							if (x + x2 < 0 || x + x2 > 4) continue;
							NSLog(@"C");
							
							id tile = [self.tileLabels objectAtIndex:(int) x + x2 + 5 * (y + y2)];
							if ([tile isKindOfClass:[UILabel class]]) { // If there is a tile in that spot...
																		// [tile setFrame:[Game2ViewController rectanglePoint:[tile center]]];
								[(UILabel *) tile setTransform:CGAffineTransformScale(CGAffineTransformIdentity, 0.00001, 0.00001)]; // Shrink the tile (i.e. destroy)
								[self.tileArray setObject:@0 atIndexedSubscript:(int) x + x2 + 5 * (y + y2)]; // Remove the tile from the tile array
								[self.tileLabels setObject:@0 atIndexedSubscript:(int) x + x2 + 5 * (y + y2)]; // Remove the tile from the tile label array
							}
							
							NSLog(@"D");
						}
					}
					NSLog(@"Q");
				} completion:^(BOOL finished) {
					for (int y2 = -1; y2 <= 1; y2++) { // Go through each tile in a 3x3 square around the dropped tile
						if (y + y2 < 0 || y + y2 > 5) continue;
						for (int x2 = -1; x2 <= 1; x2++) {
							if (x + x2 < 0 || x + x2 > 4) continue;
							
							id tile = [self.tileLabels objectAtIndex:(int) x + x2 + 5 * (y + y2)];
							if ([tile isKindOfClass:[UILabel class]]) { // If there is a tile in that spot...
								[tile removeFromSuperview]; // Remove it from the view
							}
						}
					}
					
					NSLog(@"R");
					[UIView animateWithDuration:0.25 delay:0 options:UIViewAnimationOptionCurveLinear animations:^{
						for (int x = 0; x < 5; x++) { // Have all tiles fall.
							for (int y = 0; y < 6; y++) {
								int newY = [self lowestSpaceInColumn:x]; // Find the Y value to fall to
								if (newY > y) continue; // Don't drop tiles that cannot be dropped further
								NSLog(@"ASL %i %i %i", x, y, newY);
								// Drop the tile
								self.tileArray[x + 5 * newY] = self.tileArray[x + 5 * y];
								// Remove the original tile
								self.tileArray[x + 5 * y] = @0;
								NSLog(@"KK");
								//Repeat for the labels
								self.tileLabels[x + 5 * newY] = self.tileLabels[x + 5 * y];
								self.tileLabels[x + 5 * y] = @0;
								if ([self.tileLabels[x + 5 * newY] isKindOfClass:[UILabel class]]) { // If the label is actually a label...
									[UIView animateWithDuration:0.25 delay:0 options:UIViewAnimationOptionCurveLinear animations:^{ // Update its position
										[[self.tileLabels objectAtIndex:x + 5 * newY] setFrame:[self frameForTileAtX:x Y:newY withOffset:0]];
									} completion:nil];
								}
							}
							// Move the buttons
							//							int n = [self lowestSpaceInColumn:x];
							//							[(UIButton *) self.buttons[x] setEnabled:(n < 255)];
							//							[(UIButton *) self.buttons[x] setAlpha:(n < 255 ? 0.5 : 0)];
							//							[self.buttons[x] setFrame:[self frameForTileAtX:x Y:n withOffset:0]];
						}
						NSLog(@"S");
					} completion:completion];
				}];
			}];
			return;
		}
	}
	completion(YES);
}

- (NSSet *)setOfInvalidValuesFromMovedCells:(NSSet *)cells {
	NSMutableSet *ret = [[NSMutableSet alloc] init];
	
	for (id cell in cells) {
		if (![cell isKindOfClass:[NSNumber class]]) continue;
		int c = [cell intValue];
		int x = c % 5;
		int y = c / 5;
		NSNumber *v = [self.tileArray objectAtIndex:c];
		for (Rule *rule in rules) {
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

//- (void)destroyAndDropAroundCellsInSet:(NSSet *)set completion:(void (^)(BOOL finished))completion {
//	NSLog(@"%i", (int) set.count);
//	
//	if (set.count == 0) {
//		if (completion) completion(YES);
//		return;
//	}
//	
//	NSSet *toDestroy = [self processViolatingSet:set completion:^(BOOL finished) {
//		
//	}];
//		[UIView animateWithDuration:0.5 animations:^{
//				for (id k in toDestroy) {
//					if (![k isKindOfClass:[NSNumber class]]) continue;
//					
//					// Remove the cell from the numerical array
//					self.tileArray[[k integerValue]] = @0;
//					
//					// Shrink (destroy) all of the affected cells
//					id label = [self.tileLabels objectAtIndex:[k integerValue]];
//					if ([label isKindOfClass:[UILabel class]])
//						[(UILabel *) label setTransform:CGAffineTransformScale(CGAffineTransformIdentity, 0.00001, 0.00001)];
//				}
//			} completion:^(BOOL finished) { // When they all have been destroyed...
//				NSMutableSet *moved = [[NSMutableSet alloc] init]; // A set to store the tiles that have moved and can therefore
//																   // Be potential candidates for an additional rule violation
//				
//				// Have all floating tiles fall
//				[UIView animateWithDuration:0.25 delay:0 options:UIViewAnimationOptionCurveLinear animations:^{
//					for (int x = 0; x < 5; x++) { // Iterate through each tile to test if it can fall
//						for (int y = 0; y < 6; y++) {
//							int newY = [self lowestSpaceInColumn:x]; // Find the Y value to fall to
//							if (newY > y) continue; // Don't drop tiles that cannot be dropped further
//							
//							self.tileArray[x + 5 * newY] = self.tileArray[x + 5 * y]; // Drop the tile
//							self.tileArray[x + 5 * y] = @0; // Remove the original tile
//							
//							// Repeat for the labels
//							self.tileLabels[x + 5 * newY] = self.tileLabels[x + 5 * y];
//							self.tileLabels[x + 5 * y] = @0;
//							
//							// The tile moved; add it to the set
//							[moved addObject:[NSNumber numberWithInt:x + 5 * newY]];
//							
//							if ([self.tileLabels[x + 5 * newY] isKindOfClass:[UILabel class]]) { // If the label is actually a label...
//								[UIView animateWithDuration:0.25 delay:0 options:UIViewAnimationOptionCurveLinear animations:^{ // Update its position
//									[[self.tileLabels objectAtIndex:x + 5 * newY] setFrame:[self frameForTileAtX:x Y:newY withOffset:0]];
//								} completion:nil];
//							}
//						}
//					}
//				} completion:^(BOOL finished) { // When all tiles have finished falling
//					// Repeat the process (recursively) by calling this method with a new set.
//					[self destroyAndDropAroundCellsInSet:[self setOfInvalidValuesFromMovedCells:moved] completion:completion];
//					// if (completion) completion(finished);
//				}];
//			}];
//		} : nil];
//	}
//}

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
		b = NO;
	}
}

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

- (void)buttonPressed:(UIButton *)sender {
	if (dropping) return;
	dropping = YES;
	
	int x = (int) (sender.center.x / size);
	int y = [self lowestSpaceInColumn:x];//(int) ((self.view.frame.size.height - sender.center.y) / size);
	
	self.tileArray[x + 5 * y] = [NSNumber numberWithInteger:numberToDrop];
	self.tileLabels[x + 5 * y] = toDrop;
	
	[self dropTile:toDrop toX:x Y:y andReplaceButton:sender prepare:YES];
}

- (void)prepareToDropWithButton:(UIButton *)buttonToMove atX:(NSUInteger)x Y:(NSUInteger)y {
	numberToDrop = 1 + arc4random() % 9;
	
	toDrop = [[UILabel alloc] initWithFrame:[self frameForTileAtX:2 Y:6 withOffset:-16]];
	[toDrop setText:[NSString stringWithFormat:@"%li", (long) numberToDrop]];
	[toDrop setFont:[UIFont fontWithName:@"Montserrat" size:48]];
	[toDrop setTextAlignment:NSTextAlignmentCenter];
	[toDrop setBackgroundColor:[UIColor lightGrayColor]];
	toDrop.alpha = 0;
	
	for (int i = 0; i < 5; i++) {
		UIButton *button = (UIButton *) [buttons objectAtIndex:i];
		[UIView transitionWithView:button duration:0.25 options:UIViewAnimationOptionTransitionCrossDissolve animations:^{
			[button setTitle:[NSString stringWithFormat:@"%li", (long) numberToDrop] forState:UIControlStateNormal];
		} completion:^(BOOL finished) {
			[self startAlphaFlash:button];
		}];
	}
	
	[UIView animateWithDuration:0.25 delay:0 options:UIViewAnimationOptionCurveLinear animations:^{
		for (int x = 0; x < 5; x++) { // Raise the buttons if no rules were violated.
			int n = [self lowestSpaceInColumn:x];
			[(UIButton *) self.buttons[x] setEnabled:(n < 255)];
			[(UIButton *) self.buttons[x] setAlpha:(n < 255 ? 0.5 : 0)];
			[self.buttons[x] setFrame:[self frameForTileAtX:x Y:n withOffset:0]];
		}
	} completion:nil];
	
	//	if (buttonToMove) {
	//		[UIView animateWithDuration:0.25 animations:^{
	//			[buttonToMove setFrame:[self frameForTileAtX:x Y:y + 1 withOffset:0]];
	//		}];
	//	}
	
	[self.view addSubview:toDrop];
	[UIView animateWithDuration:0.25 animations:^{
		toDrop.alpha = 1;
	}];
}

- (void)startAlphaFlash:(UIButton *)button {
	[button setAlpha:0.6];
	[UIView animateWithDuration:1 delay:0 options:UIViewAnimationOptionAutoreverse | UIViewAnimationOptionRepeat | UIViewAnimationOptionAllowUserInteraction animations:^{
		[button setAlpha:0.4];
	} completion:nil];
}

- (CGPoint)centerPointForTileAtX:(NSUInteger)x Y:(NSUInteger)y {
	return CGPointMake((x + 0.5) * size, self.view.frame.size.height - (y + 0.5) * size);
}

- (CGRect)frameForTileAtX:(NSUInteger)x Y:(NSUInteger)y withOffset:(CGFloat)offsetY {
	return CGRectMake(x * size + 5, self.view.frame.size.height - (y + 1) * size + 5 + offsetY, size - 10, size - 10);
}

+ (CGRect)rectanglePoint:(CGPoint)center {
	return CGRectMake(center.x, center.y, 0, 0);
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
