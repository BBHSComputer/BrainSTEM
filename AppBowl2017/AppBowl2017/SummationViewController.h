//
//  SummationViewController.h
//  AppBowl2017
//
//  Created by Ethan Tillison on 27/4/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SummationEndViewController.h"
#import "SimpleQuestion.h"
#import "BiggestQuestion.h"
#import "TimedQuestion.h"

@interface SummationViewController : UIViewController <QuestionDelegate>

@property (strong, nonatomic) NSMutableArray *questions;
@property (nonatomic) int currentQuestion;

@property (nonatomic) int correctCount;

@property (strong, nonatomic) IBOutlet UILabel *questionTitle, *correctLabel;
@property (strong, nonatomic) IBOutlet UIButton *button1, *button2, *button3, *button4;

- (IBAction)unwindToPlayAgain:(UIStoryboardSegue *)segue;

@end
