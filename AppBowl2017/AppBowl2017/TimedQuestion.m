//
//  TimedQuestion.m
//  AppBowl2017
//
//  Created by Ethan Tillison on 28/4/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import "TimedQuestion.h"

@implementation TimedQuestion

- (instancetype)initWithTitle:(NSString *)title options:(NSArray *)options correctOption:(int)option time:(NSTimeInterval)time {
	self = [super init];
	if (self) { // Standard initializer (sets variables)
		self.title = title;
		self.options = options;
		self.answer = option;
		self.time = time;
		self.timeElapsed = NO;
	}
	return self;
}

- (void)configureQuestionInView:(UIView *)view title:(UILabel *)title buttons:(NSArray *)buttons {
	[title setText:self.title];
	for (int i = 0; i < 4; i++) {
		// Set the title of the button
		[buttons[i] setTitle:[NSString stringWithFormat:@"%@", self.options[i]] forState:UIControlStateNormal];
		// Call buttonPressed: when the button is pressed
		[buttons[i] addTarget:self action:@selector(buttonPressed:) forControlEvents:UIControlEventTouchUpInside];
		if (i == self.answer) [buttons[i] setTag:11610]; // Tag the correct answer
	}
	
	[self performSelector:@selector(triggerTimeElapsed:) withObject:buttons[self.answer] afterDelay:self.time];
}

- (void)finishQuestionInView:(UIView *)view title:(UILabel *)title buttons:(NSArray *)buttons {
	for (int i = 0; i < 4; i++) {
		[buttons[i] removeTarget:nil action:nil forControlEvents:UIControlEventAllEvents];
		[(UIButton *) buttons[i] setTransform:CGAffineTransformIdentity];
	}
}

- (void)triggerTimeElapsed:(UIButton *)button {
	if (self.timeElapsed) return;
	
	self.timeElapsed = YES;
	[UIView animateWithDuration:0.25 animations:^{
		[button setTransform:CGAffineTransformScale(CGAffineTransformIdentity, 1.5, 1.5)];
	}];
}

- (void)buttonPressed:(UIButton *)button {
	self.timeElapsed = YES;
	
	// The answer is only correct until after the time has elapsed
	if (self.delegate) [self.delegate questionAnswered:self.timeElapsed && button.tag == 11610];
}

@end
