//
//  MusicViewController.h
//  AppBowl2017
//
//  Created by Ethan Tillison on 26/4/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MusicViewController : UIViewController

extern CGFloat const SIZE;

@property (nonatomic) int song;
@property (strong, nonatomic) UILabel *countdown, *score;

@end
