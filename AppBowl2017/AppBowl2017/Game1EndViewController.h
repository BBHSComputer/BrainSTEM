//
//  Game1EndViewController.h
//  AppBowl2017
//
//  Created by Ethan Tillison on 3/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface Game1EndViewController : UIViewController

@property (strong, nonatomic) IBOutlet UILabel *time, *moves;
@property (strong, nonatomic) NSString *timeString, *movesString;

@end
