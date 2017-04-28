//
//  BiggestQuestion.m
//  AppBowl2017
//
//  Created by Ethan Tillison on 28/4/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import "BiggestQuestion.h"

@implementation BiggestQuestion

- (instancetype)initWithTitle:(NSString *)title options:(NSArray *)options {
	self = [super init];
	if (self) {
		self.title = title;
		self.options = options;
	}
	return self;
}

- (void)configureQuestionInView:(UIView *)view title:(UILabel *)title buttons:(NSArray *)buttons {
	[title setText:self.title];
	for (int i = 0; i < 4; i++) {
		[buttons[i] setTitle:[NSString stringWithFormat:@"%@", self.options[i]] forState:UIControlStateNormal];
		[buttons[i] addTarget:self action:@selector(buttonPressed:) forControlEvents:UIControlEventTouchUpInside];
	}
}

- (void)finishQuestionInView:(UIView *)view title:(UILabel *)title buttons:(NSArray *)buttons {
	for (int i = 0; i < 4; i++) {
		[buttons[i] removeTarget:nil action:nil forControlEvents:UIControlEventAllEvents];
		[(UIButton *) buttons[i] setTransform:CGAffineTransformIdentity];
	}
}

- (void)buttonPressed:(UIButton *)button {
	if (self.correctButton) {
		if (self.delegate) [self.delegate questionAnswered:button == self.correctButton];
	} else {
		self.correctButton = button;
		[UIView animateWithDuration:0.25 animations:^{
			[button setTransform:CGAffineTransformScale(CGAffineTransformIdentity, 1.5, 1.5)];
		}];
	}
}

@end
