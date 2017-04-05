//
//  Game1ViewController.m
//  AppBowl2017
//
//  Created by Ethan Tillison on 2/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import "Game1ViewController.h"
#import "TileImageView.h"

@interface Game1ViewController ()

@end

@implementation Game1ViewController

NSInteger gameTime = 0;
int gameMoves = 0;

int selections = 0;
int flipping = 0;
TileImageView *a, *b;

@synthesize time, moves, images, imageViews;

- (void)layoutImages:(int)numberOfImages {
	imageViews = [[NSMutableArray alloc] init];
	
	int cellSize, numCols;
	
	int dy = 100;//(int) (self.navigationController.navigationBar.frame.size.height + self.navigationController.navigationBar.frame.origin.y);
	
	int w = self.view.frame.size.width;
	int h = self.view.frame.size.height - dy;
	int n = numberOfImages * 2;
	
	// the number of squares with which we can fill the x axis, given the maximum side of a
	// square must be (width * height / num). w/sqrt(wh/n)=sqrt(wn/h)
	int numSqWideFitX = (int) ceil(sqrt(w * n / h));
	double sqSizeFitX = w / numSqWideFitX;
	int numSqTallFitX = (int) ceil((double) n / numSqWideFitX);
	if (sqSizeFitX * numSqTallFitX > h) sqSizeFitX = -1;
	
	int numSqTallFitY = (int) ceil(sqrt(h * n / w));
	double sqSizeFitY = h / numSqTallFitY;
	int numSqWideFitY = (int) ceil((float) n / numSqTallFitY);
	if (sqSizeFitY * numSqWideFitY > w) sqSizeFitY = -1;
	
	if (sqSizeFitX > sqSizeFitY) {
		cellSize = sqSizeFitX;
		numCols = numSqWideFitX;
	} else {
		if (sqSizeFitY == -1) return;
		cellSize = sqSizeFitY;
		numCols = numSqWideFitY;
	}
	
	int dx = (w - cellSize * numCols) / 2;
	
	NSMutableArray *cards = [[NSMutableArray alloc] init];
	for (int i = 0; i < numberOfImages; i++) {
		[cards addObject:[NSNumber numberWithInt:i]];
		[cards addObject:[NSNumber numberWithInt:i]];
	}

	NSUInteger count = numberOfImages * 2;
	for (NSUInteger i = 0; i < count; ++i) {
		// Select a random element between i and end of array to swap with.
		NSInteger nElements = count - i;
		NSInteger n = (arc4random() % nElements) + i;
		[cards exchangeObjectAtIndex:i withObjectAtIndex:n];
	}
	
	for (int i = 0; i < n; i++) {
		int cardNumber = [(NSNumber *) [cards objectAtIndex:i] intValue];
		
		TileImageView *card = [[TileImageView alloc] initWithImage:[UIImage imageNamed:@"CardBack"]];
		[card setHighlightedImage:[images objectAtIndex:cardNumber]];
		card.imageId = cardNumber;
		[card setContentMode:UIViewContentModeScaleAspectFit];
		
		int x = i % numCols;
		int y = i / numCols;
		card.frame = CGRectMake(dx + x * cellSize + 8, dy + y * cellSize + 8, cellSize - 16, cellSize - 16);
		card.x = x;
		card.y = y;
		
		UIButton *button = [[UIButton alloc] init];
		[button setTag:i];
		[button addTarget:self action:@selector(imagePressed:) forControlEvents:UIControlEventTouchUpInside];
		button.frame = CGRectMake(dx + x * cellSize + 8, dy + y * cellSize + 8, cellSize - 16, cellSize - 16);
		
		[self.view addSubview:card];
		[self.view addSubview:button];
		
		[imageViews addObject:card];
	}
}

- (void)flipImage:(NSInteger)image {
	TileImageView *imageView = (TileImageView *) [imageViews objectAtIndex:image];
	if (imageView.matched || imageView.flipped == FlippingStateFlipping || imageView.flipped == FlippingStateUnflipping) return;
		
	[UIView transitionWithView:imageView duration:0.5 options:UIViewAnimationOptionTransitionFlipFromRight animations:^{
		[imageView setImage:imageView.flipped == FlippingStateUnflipped ? (imageView.imageId >= [images count] ? nil : [images objectAtIndex:imageView.imageId]) : [UIImage imageNamed:@"CardBack"]];
		imageView.flipped = imageView.flipped == FlippingStateUnflipped ? FlippingStateFlipping : FlippingStateUnflipping;
		if (imageView.flipped == FlippingStateFlipping) flipping++;
	} completion:^(BOOL finished) {
		if (imageView.flipped == FlippingStateFlipping) flipping--;
		imageView.flipped = imageView.flipped == FlippingStateFlipping ? FlippingStateFlipped : FlippingStateUnflipped;
		selections += imageView.flipped == FlippingStateFlipped ? 1 : -1;
		
		if (selections >= 2) {
			for (int i = 0; i < imageViews.count; i++) {
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
			gameMoves++;
			[moves setText:[NSString stringWithFormat:@"Moves: %i", gameMoves]];

			if (a.imageId != b.imageId) {
				[self performSelector:@selector(unflip) withObject:nil afterDelay:0.75];
			} else {
				[UIView animateWithDuration:0.5 animations:^{
					a.frame = CGRectMake(self.view.frame.size.width / 2, 0, 0, 0);
					b.frame = CGRectMake(self.view.frame.size.width / 2, 0, 0, 0);
					a.alpha = b.alpha = 0;
				}];
				
				a.matched =	b.matched = YES;
				BOOL unmatched = NO;
				for (int i = 0; i < imageViews.count; i++) {
					TileImageView *image = (TileImageView *) [imageViews objectAtIndex:i];
					if (!image.matched) {
						unmatched = YES;
						break;
					}
				}
				if (!unmatched) {
					gameTime = -1;
					[self performSegueWithIdentifier:@"Win" sender:self];
				}
				a = b = nil;
				selections = 0;
			}
		}
	}];
}

- (void)unflip {
	[UIView transitionWithView:a duration:0.5 options:UIViewAnimationOptionTransitionFlipFromRight animations:^{
		a.flipped = FlippingStateUnflipping;
		[a setImage:[UIImage imageNamed:@"CardBack"]];
	} completion:^(BOOL finished) {
		a.flipped = FlippingStateUnflipped;
		selections--;
		a = nil;
	}];
	[UIView transitionWithView:b duration:0.5 options:UIViewAnimationOptionTransitionFlipFromRight animations:^{
		b.flipped = FlippingStateUnflipping;
		[b setImage:[UIImage imageNamed:@"CardBack"]];
	} completion:^(BOOL finished) {
		b.flipped = FlippingStateUnflipped;
		selections--;
		b = nil;
	}];
}

- (void)imagePressed:(UIButton *)sender {
	if (selections + flipping < 2) {
		[self flipImage:sender.tag];
	}
}

- (void)reset {
	gameTime = 0;
	gameMoves = 0;
}

- (void)incrementTime {
	if (gameTime >= 0) {
		gameTime++;
		NSInteger seconds = gameTime % 60;
		NSInteger minutes = (gameTime / 60) % 60;
		NSInteger hours = (gameTime / 3600);
		[time setText:[NSString stringWithFormat:@"Time: %li:%02li:%02li", (long)hours, minutes, seconds]];
		[self performSelector:@selector(incrementTime) withObject:nil afterDelay:1];
	}
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)viewDidAppear:(BOOL)animated {
	[super viewDidAppear:animated];
	[self incrementTime];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
	if ([segue.identifier isEqualToString:@"Win"]) {
		Game1EndViewController *end = (Game1EndViewController *) [segue destinationViewController];
		[end setTimeString:[self.time text]];
		[end setMovesString:[self.moves text]];
	}
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}

@end
