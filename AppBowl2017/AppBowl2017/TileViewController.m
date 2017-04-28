//
//  TileViewController.m
//  AppBowl2017
//
//  Created by Ethan Tillison on 2/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import "TileViewController.h"
#import "TileImageView.h"

@interface TileViewController ()

@end

@implementation TileViewController

// Autosynthsize the setters/getters for these properties (from the header file)
@synthesize time, moves, images, imageViews;
@synthesize gameTime, gameMoves, selections, flipping, a, b;

/// Layout the requested number of images
- (void)layoutImages:(int)numberOfImages {
	// Instantiate the array of image views
	imageViews = [[NSMutableArray alloc] init];
	
	int cellSize, numCols; // The length of the side of a cell, and the number of columns of cells, respectively. To be calculated.
	
	int dy = 100; // The vertical offset (to account for the navbar)
	
	int w = self.view.frame.size.width; // The width of the screen
	int h = self.view.frame.size.height - dy; // The height of the screen
	int n = numberOfImages * 2; // The number of images to create (2x the requested number, so each has a pair)
	
	// The number of squares with which we can fill the x axis, given the maximum side of a
	// square must be (width * height / num). w/sqrt(wh/n)=sqrt(wn/h)
	int numSqWideFitX = (int) ceil(sqrt(w * n / h));
	double sqSizeFitX = w / numSqWideFitX; // The side length of a square given the x axis is filled
	int numSqTallFitX = (int) ceil((double) n / numSqWideFitX); // The number of squares that will be fit vertically, given the x axis is filled.
	if (sqSizeFitX * numSqTallFitX > h) sqSizeFitX = -1; // If there are too many squares vertically, set to -1 to ensure this value is not used.
	
	// Repeat to fit y axis instead of x
	int numSqTallFitY = (int) ceil(sqrt(h * n / w));
	double sqSizeFitY = h / numSqTallFitY;
	int numSqWideFitY = (int) ceil((float) n / numSqTallFitY);
	if (sqSizeFitY * numSqWideFitY > w) sqSizeFitY = -1;
	
	if (sqSizeFitX > sqSizeFitY) { // Pick the size with the bigger square size
		cellSize = sqSizeFitX;
		numCols = numSqWideFitX;
	} else {
		if (sqSizeFitY == -1) return; // If both were invalid, don't do anything.
		cellSize = sqSizeFitY;
		numCols = numSqWideFitY;
	}
	
	int dx = (w - cellSize * numCols) / 2; // The x offset, to center the tiles.
	
	// The numbers of the cards
	NSMutableArray *cards = [[NSMutableArray alloc] init];
	for (int i = 0; i < numberOfImages; i++) {
		[cards addObject:[NSNumber numberWithInt:i]];
		[cards addObject:[NSNumber numberWithInt:i]];
	}

	// Shuffle the cards.
	NSUInteger count = numberOfImages * 2;
	for (NSUInteger i = 0; i < count; ++i) {
		// Select a random element between i and end of array to swap with.
		NSInteger nElements = count - i;
		NSInteger n = (arc4random() % nElements) + i;
		[cards exchangeObjectAtIndex:i withObjectAtIndex:n];
	}
	
	for (int i = 0; i < n; i++) { // Create the image views
		// Get the imageindex from the shuffled list
		int cardNumber = [(NSNumber *) [cards objectAtIndex:i] intValue];
		
		// Create a card and configure it
		TileImageView *card = [[TileImageView alloc] initWithImage:[UIImage imageNamed:@"AppIcon"]];
		[card setHighlightedImage:[images objectAtIndex:cardNumber]];
		card.imageId = cardNumber;
		[card setContentMode:UIViewContentModeScaleAspectFit];
		
		int x = i % numCols; // Calculate x and y based on i
		int y = i / numCols;
		// Position the card
		card.frame = CGRectMake(dx + x * cellSize + 8, dy + y * cellSize + 8, cellSize - 16, cellSize - 16);
		card.x = x;
		card.y = y;
		
		// Create a button to go over the image, to process input. This button will be entirely transparent.
		UIButton *button = [[UIButton alloc] init];
		[button setTag:i];
		[button addTarget:self action:@selector(imagePressed:) forControlEvents:UIControlEventTouchUpInside];
		button.frame = CGRectMake(dx + x * cellSize + 8, dy + y * cellSize + 8, cellSize - 16, cellSize - 16);
		
		// Add all to view.
		[self.view addSubview:card];
		[self.view addSubview:button];
		
		[imageViews addObject:card];
	}
}

/// Animate the flip transition of the given image
- (void)flipImage:(NSInteger)image {
	// Get the image view to flip.
	TileImageView *imageView = (TileImageView *) [imageViews objectAtIndex:image];
	
	// If it's already flipping, don't do anything
	if (imageView.matched || imageView.flipped == FlippingStateFlipping || imageView.flipped == FlippingStateUnflipping) return;
	
	// Add one to the moves counter and update the display
	gameMoves++;
	[moves setText:[NSString stringWithFormat:@"Moves: %i", gameMoves]];
	
	// Animate flipping
	[UIView transitionWithView:imageView duration:0.5 options:UIViewAnimationOptionTransitionFlipFromRight animations:^{
		// Configure state based on previous states (Flipped -> Unflipping, Unflipped -> Flipping)
		[imageView setImage:imageView.flipped == FlippingStateUnflipped ? (imageView.imageId >= [images count] ? nil : [images objectAtIndex:imageView.imageId]) : [UIImage imageNamed:@"CardBack"]];
		imageView.flipped = imageView.flipped == FlippingStateUnflipped ? FlippingStateFlipping : FlippingStateUnflipping;
		if (imageView.flipped == FlippingStateFlipping) flipping++;
	} completion:^(BOOL finished) {
		if (imageView.flipped == FlippingStateFlipping) flipping--;
		imageView.flipped = imageView.flipped == FlippingStateFlipping ? FlippingStateFlipped : FlippingStateUnflipped;
		selections += imageView.flipped == FlippingStateFlipped ? 1 : -1; // If this image has been selected, add 1 to selections, otherwise, it has been deselected, so remove 1
		
		if (selections >= 2) { // If 2 images are selected...
			for (int i = 0; i < imageViews.count; i++) { // Find the flipped images and store them in a and b
				TileImageView *image = (TileImageView *) [imageViews objectAtIndex:i];
				if (image.flipped == FlippingStateFlipped && !image.matched) {
					if (!a) {
						a = image;
					} else if (!b) {
						b = image;
						break;
					}
				}
			}

			if (a.imageId != b.imageId) { // If they don't match, unflip them
				[self performSelector:@selector(unflip) withObject:nil afterDelay:0.75];
			} else { // If they do match...
				[UIView animateWithDuration:0.5 animations:^{ // Send the off the screen
					a.frame = CGRectMake(self.view.frame.size.width / 2, 0, 0, 0);
					b.frame = CGRectMake(self.view.frame.size.width / 2, 0, 0, 0);
					a.alpha = b.alpha = 0;
				}];
				
				a.matched =	b.matched = YES; // Set the matched flag so they cannot be pressed again
				
				// Check to see if the player has won (if there are no unmatched tiles left)
				BOOL unmatched = NO;
				for (int i = 0; i < imageViews.count; i++) {
					TileImageView *image = (TileImageView *) [imageViews objectAtIndex:i];
					if (!image.matched) {
						unmatched = YES;
						break;
					}
				}
				if (!unmatched) { // If the player has won...
					gameTime = -1;
					[self performSegueWithIdentifier:@"Win" sender:self];
				}
				a = b = nil;
				selections = 0;
			}
		}
	}];
}

/// Unflip (flip from image to back state) a and b
- (void)unflip {
	// Flip a
	[UIView transitionWithView:a duration:0.5 options:UIViewAnimationOptionTransitionFlipFromRight animations:^{
		a.flipped = FlippingStateUnflipping;
		[a setImage:[UIImage imageNamed:@"AppIcon"]];
	} completion:^(BOOL finished) {
		a.flipped = FlippingStateUnflipped;
		selections--;
		a = nil;
	}];
	
	// Flip b
	[UIView transitionWithView:b duration:0.5 options:UIViewAnimationOptionTransitionFlipFromRight animations:^{
		b.flipped = FlippingStateUnflipping;
		[b setImage:[UIImage imageNamed:@"AppIcon"]];
	} completion:^(BOOL finished) {
		b.flipped = FlippingStateUnflipped;
		selections--;
		b = nil;
	}];
}

/// Called when an image is tapped
- (void)imagePressed:(UIButton *)sender {
	if (selections + flipping < 2) {
		[self flipImage:sender.tag];
	}
}

/// Reset the statistics
- (void)reset {
	gameTime = 0;
	gameMoves = 0;
}

/// Set the game time every second
- (void)incrementTime {
	if (gameTime >= 0) { // If the game hasn't ended yet
		gameTime++;
		NSInteger seconds = gameTime % 60; // Split seconds into h/m/s
		NSInteger minutes = (gameTime / 60) % 60;
		NSInteger hours = (gameTime / 3600);
		
		// Format the time string
		[time setText:[NSString stringWithFormat:@"Time: %li:%02li:%02li", (long)hours, minutes, seconds]];
		
		// Repeat in 1 second
		[self performSelector:@selector(incrementTime) withObject:nil afterDelay:1];
	}
}

- (void)viewDidLoad {
    [super viewDidLoad];
	
	gameTime = 0;
	gameMoves = 0;
	selections = 0;
	flipping = 0;
}

- (void)viewDidAppear:(BOOL)animated {
	[super viewDidAppear:animated];
	[self incrementTime]; // Start the timer
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
	if ([segue.identifier isEqualToString:@"Win"]) { // When the game is won...
		TileEndViewController *end = (TileEndViewController *) [segue destinationViewController];
		[end setTimeString:[self.time text]]; // Send the time...
		[end setMovesString:[self.moves text]]; // ...and the number of moves to the "You Win!" screen.
	}
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}

@end
