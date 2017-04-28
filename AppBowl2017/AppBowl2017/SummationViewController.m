//
//  SummationViewController.m
//  AppBowl2017
//
//  Created by Ethan Tillison on 27/4/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import "SummationViewController.h"

@interface SummationViewController ()

@end

@implementation SummationViewController

- (void)viewDidLoad {
    [super viewDidLoad];
	
	[self.correctLabel removeFromSuperview];
	
	self.questions = [@[[[SimpleQuestion alloc] initWithTitle:@"On which parking space is the car parked?" options:@[@"78", @"89", @"87", @"97"] correctOption:2],
					   [[SimpleQuestion alloc] initWithTitle:@"A and C but not B" options:@[@"A: A", @"B: B, A, and D", @"C: C and D", @"D: D and A"] correctOption:2],
					   [[SimpleQuestion alloc] initWithTitle:@"Where is I?" options:@[@"Here", @"There", @"Hair", @"Anywhere"] correctOption:2],
					   [[SimpleQuestion alloc] initWithTitle:@"Choose the answer closest to the correct answer" options:@[@"The Answer", @"The Answer", @"The Answer", @"The Answer"] correctOption:0],
					   [[BiggestQuestion alloc] initWithTitle:@"Click on the biggest answer" options:@[@"Answer!", @"Answer.", @"Big", @"Answer?"]],
					   [[TimedQuestion alloc] initWithTitle:@"Run out of time" options:@[@"Done", @"Run", @"No", @"What?"] correctOption:0 time:5]] mutableCopy];
	
	for (int n = 0; n < self.questions.count; n++) {
		[self.questions[n] setDelegate:self];
	}
	
	for (NSInteger x = 0; x < [self.questions count]; x++) {
		NSInteger randInt = (arc4random() % ([self.questions count] - x)) + x;
		[self.questions exchangeObjectAtIndex:x withObjectAtIndex:randInt];
	}
	
	self.currentQuestion = 0;
	[self.questions[self.currentQuestion] configureQuestionInView:self.view title:self.questionTitle buttons:@[self.button1, self.button2, self.button3, self.button4]];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)nextQuestion {
	[UIView transitionWithView:self.view duration:0.25 options:UIViewAnimationOptionTransitionCrossDissolve animations:^{
		[self.correctLabel removeFromSuperview];
		
		[self.questions[self.currentQuestion++] finishQuestionInView:self.view title:self.questionTitle buttons:@[self.button1, self.button2, self.button3, self.button4]];
		if (self.currentQuestion == self.questions.count) {
			[self performSegueWithIdentifier:@"EndGame" sender:self];
			return;
		}
		[self.questions[self.currentQuestion] configureQuestionInView:self.view title:self.questionTitle buttons:@[self.button1, self.button2, self.button3, self.button4]];
	} completion:nil];
}

- (void)questionAnswered:(BOOL)correct {
	if (correct) self.correctCount++;
	
	self.correctLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
	[self.correctLabel setFont:[UIFont fontWithName:@"Montserrat" size:48]];
	[self.correctLabel setTextAlignment:NSTextAlignmentCenter];
	[self.correctLabel setText:correct ? @"CORRECT" : @"INCORRECT"];
	[self.correctLabel setTextColor:[UIColor colorWithRed:correct ? 0 : 0.5 green:correct ? 0.5 : 0 blue:0 alpha:1]];
	[self.view addSubview:self.correctLabel];
	[self performSelector:@selector(nextQuestion) withObject:nil afterDelay:2];
}

- (IBAction)unwindToPlayAgain:(UIStoryboardSegue *)segue {}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
	if ([[segue identifier] isEqualToString:@"EndGame"]) {
		if ([[segue destinationViewController] isKindOfClass:[SummationEndViewController class]]) {
			[(SummationEndViewController *) segue.destinationViewController setScoreString:[NSString stringWithFormat:@"Correct: %i/%i", self.correctCount, (int) self.questions.count]];
		}
	}
}

@end
