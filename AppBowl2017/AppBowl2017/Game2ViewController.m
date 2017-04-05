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
		[button setAlpha:0.5];
		[button.titleLabel setFont:[UIFont fontWithName:@"Montserrat" size:48]];
		[button setBackgroundColor:[UIColor lightGrayColor]];
	}
	
	[self prepareToDropWithButton:nil atX:0 Y:0];
	
	rules = [@[[[AdjacencyRule alloc] initWithTile:[NSNumber numberWithInt:arc4random() % 9 + 1] and:[NSNumber numberWithInt:arc4random() % 9 + 1]]] mutableCopy];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)dropTile:(UIView *)tile toX:(NSUInteger)x Y:(NSUInteger)y andReplaceButton:(UIButton *)button {
	[UIView animateWithDuration:0.05 + 0.1 * abs(2 - (int) x) delay:0 options:UIViewAnimationOptionCurveLinear animations:^{
		tile.center = [self centerPointForTileAtX:x Y:6];
	} completion:^(BOOL finished) {
		[UIView animateWithDuration:0.1 * abs(6 - (int) y) delay:0 options:UIViewAnimationOptionCurveLinear animations:^{
			tile.center = [self centerPointForTileAtX:x Y:y];
		} completion:^(BOOL finished) {
			if (y >= 5) {
				[button setEnabled:false];
				[button removeFromSuperview];
			}
			
			[self validateDropAtX:x Y:y completion:^(BOOL finished) {
				NSLog(@"XXXXXXXXXXXX");
				[self prepareToDropWithButton:y >= 5 ? nil : button atX:x Y:y];
				dropping = NO;
			}];
		}];
	}];
}

- (void)validateDropAtX:(NSUInteger)x Y:(NSUInteger)y completion:(void (^)(BOOL finished)) completion {
	for (Rule *r in rules) {
		NSLog(@"%@", (x > 0 && ![r isNewTile:self.tileArray[x + 5 * y] allowedNextTo:self.tileArray[x - 1 + 5 * y] inTileArray:self.tileArray]) ? @"YES" : @"NO");
		NSLog(@"%@", (x < 4 && ![r isNewTile:self.tileArray[x + 5 * y] allowedNextTo:self.tileArray[x + 1 + 5 * y] inTileArray:self.tileArray]) ? @"YES" : @"NO");
		NSLog(@"%@", (y > 0 && ![r isNewTile:self.tileArray[x + 5 * y] allowedNextTo:self.tileArray[x + 5 * y - 5] inTileArray:self.tileArray]) ? @"YES" : @"NO");
		NSLog(@"%@", (y < 5 && ![r isNewTile:self.tileArray[x + 5 * y] allowedNextTo:self.tileArray[x + 5 * y + 5] inTileArray:self.tileArray]) ? @"YES" : @"NO");
		if ((x > 0 && ![r isNewTile:self.tileArray[x + 5 * y] allowedNextTo:self.tileArray[x - 1 + 5 * y] inTileArray:self.tileArray]) ||
			(x < 4 && ![r isNewTile:self.tileArray[x + 5 * y] allowedNextTo:self.tileArray[x + 1 + 5 * y] inTileArray:self.tileArray]) ||
			(y > 0 && ![r isNewTile:self.tileArray[x + 5 * y] allowedNextTo:self.tileArray[x + 5 * y - 5] inTileArray:self.tileArray]) ||
			(y < 5 && ![r isNewTile:self.tileArray[x + 5 * y] allowedNextTo:self.tileArray[x + 5 * y + 5] inTileArray:self.tileArray])) {
			
			for (int y2 = -1; y2 <= 1; y2++) {
				for (int x2 = -1; x2 <= 1; x2++) {
					id tile = [self.tileLabels objectAtIndex:x + x2 + 5 * (y + y2)];
					if ([tile isKindOfClass:[UILabel class]]) {
						[UIView animateWithDuration:0.25 animations:^{
							[tile setFrame:[Game2ViewController rectanglePoint:[tile center]]];
						} completion:^(BOOL finished) {
							
						}];
					}
				}
			}
		}
	}
	completion(YES);
}

- (void)buttonPressed:(UIButton *)sender {
	if (dropping) return;
	dropping = YES;
	
	NSUInteger x = (NSUInteger) (sender.center.x / size);
	NSUInteger y = (NSUInteger) ((self.view.frame.size.height - sender.center.y) / size);
	
	self.tileArray[x + 5 * y] = [NSNumber numberWithInteger:numberToDrop];
	self.tileLabels[x + 5 * y] = toDrop;
	
	[self dropTile:toDrop toX:x Y:y andReplaceButton:sender];
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
		} completion:nil];
	}
	
	if (buttonToMove) {
		[UIView animateWithDuration:0.25 animations:^{
			[buttonToMove setFrame:[self frameForTileAtX:x Y:y + 1 withOffset:0]];
		}];
	}
	
	[self.view addSubview:toDrop];
	[UIView animateWithDuration:0.25 animations:^{
		toDrop.alpha = 1;
	}];
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
