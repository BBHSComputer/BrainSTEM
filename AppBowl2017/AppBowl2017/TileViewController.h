//
//  TileViewController.h
//  AppBowl2017
//
//  Created by Ethan Tillison on 2/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TileEndViewController.h"

@interface TileViewController : UIViewController

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
