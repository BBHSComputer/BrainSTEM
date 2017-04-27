//
//  TileViewController.h
//  AppBowl2017
//
//  Created by Ethan Tillison on 2/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TileEndViewController.h"
#import "TileImageView.h"

@interface TileViewController : UIViewController

// The amount of time since the game started
@property (nonatomic) NSInteger gameTime;
// The number of moves that have been made
@property (nonatomic) int gameMoves;

// How many tiles are currently selected
@property (nonatomic) int selections;
// How many tiles are currently in the flip animation.
@property (nonatomic) int flipping;
// The currently flipped tiles
@property (strong, nonatomic) TileImageView *a, *b;

// Labels showing current score
@property (strong, nonatomic) IBOutlet UILabel *time, *moves;

// The list of images
@property (strong, nonatomic) NSMutableArray *images;
// The list of image views
@property (strong, nonatomic) NSMutableArray *imageViews;

- (void)layoutImages:(int)numberOfImages;

- (void)flipImage:(NSInteger)image;

- (void)imagePressed:(UIButton *)sender;

- (void)reset;

@end
