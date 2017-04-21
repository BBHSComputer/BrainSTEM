//
//  Game2ViewController.h
//  AppBowl2017
//
//  Created by Ethan Tillison on 21/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AdjacencyRule.h"

@interface StackViewController : UIViewController

// The labels that show level and number of rules
@property (strong, nonatomic) IBOutlet UILabel *level, *ruleCount;

// The array of tile numerals; single dimensional; should be accessed as x + WIDTH * y
@property (strong, nonatomic) NSMutableArray *tileArray;
// The array of tile UILabels (also 1D)
@property (strong, nonatomic) NSMutableArray *tileLabels;

// The value of the tile that will be dropped
@property (nonatomic) NSUInteger numberToDrop;
// The label that is going to be dropped
@property (strong, nonatomic) UILabel *toDrop;
// The array of buttons to tap
@property (strong, nonatomic) NSMutableArray *buttons;

// The label that displays "Level Complete"
@property (strong, nonatomic) UILabel *levelComplete;

// The array of rules
@property (strong, nonatomic) NSMutableArray *rules;

@end
