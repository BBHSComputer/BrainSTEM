//
//  Game2ViewController.h
//  AppBowl2017
//
//  Created by Ethan Tillison on 21/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AdjacencyRule.h"

@interface Game2ViewController : UIViewController

@property (strong, nonatomic) IBOutlet UILabel *level, *ruleCount;

@property (strong, nonatomic) NSMutableArray *tileArray;
@property (strong, nonatomic) NSMutableArray *tileLabels;
@property (nonatomic) NSUInteger numberToDrop;
@property (strong, nonatomic) UILabel *toDrop;
@property (strong, nonatomic) NSMutableArray *buttons;

@property (strong, nonatomic) UILabel *levelComplete;

@property (strong, nonatomic) NSMutableArray *rules;

@end
