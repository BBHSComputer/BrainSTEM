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

int selections = 0;
TileImageView *a, *b;

@synthesize images;
@synthesize imageViews;

- (void)layoutImages:(int)numberOfImages {
	imageViews = [[NSMutableArray alloc] init];
	
	int cellSize, numCols;
	
	int dy = 64;//(int) (self.navigationController.navigationBar.frame.size.height + self.navigationController.navigationBar.frame.origin.y);
	
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
		
		TileImageView *card = [[TileImageView alloc] initWithImage:[UIImage imageNamed:@"CardBack"]]; // TODO
		[card setHighlightedImage:[images objectAtIndex:cardNumber]];
		card.imageId = cardNumber;
		
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
	if (imageView.matched) return;
	
	[UIView transitionWithView:imageView duration:0.5 options:UIViewAnimationOptionTransitionFlipFromRight animations:^{
		[imageView setImage:!imageView.flipped ? [images objectAtIndex:imageView.imageId] : [UIImage imageNamed:@"CardBack"]];
	} completion:^(BOOL finished) {
		imageView.flipped = !imageView.flipped;
		selections += imageView.flipped ? 1 : -1;
		
		if (selections >= 2) {
			for (int i = 0; i < imageViews.count; i++) {
				TileImageView *image = (TileImageView *) [imageViews objectAtIndex:i];
				if (image.flipped && !image.matched) {
					if (!a) {
						a = image;
					} else if (!b) {
						b = image;
						break;
					}
				}
			}
			if (a.imageId != b.imageId) {
				[self performSelector:@selector(unflip) withObject:nil afterDelay:1];
			} else {
				a.matched =	b.matched = YES;
				a = b = nil;
				selections = 0;
			}
		}
	}];
}

- (void)unflip {
	[UIView transitionWithView:a duration:0.5 options:UIViewAnimationOptionTransitionFlipFromRight animations:^{
		a.flipped = NO;
		[a setImage:[UIImage imageNamed:@"CardBack"]];
	} completion:^(BOOL finished) {
		selections--;
		a = nil;
	}];
	[UIView transitionWithView:b duration:0.5 options:UIViewAnimationOptionTransitionFlipFromRight animations:^{
		b.flipped = NO;
		[b setImage:[UIImage imageNamed:@"CardBack"]];
	} completion:^(BOOL finished) {
		selections--;
		b = nil;
	}];
}

- (void)imagePressed:(UIButton *)sender {
	if (selections < 2) {
		[self flipImage:sender.tag];
	}
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
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
