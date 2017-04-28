//
//  SimpleQuestion.m
//  AppBowl2017
//
//  Created by Ethan Tillison on 28/4/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import "SimpleQuestion.h"

@implementation SimpleQuestion

- (instancetype)initWithTitle:(NSString *)title options:(NSArray *)options correctOption:(int)option {
	self = [super init];
	if (self) {
		self.title = title;
		self.options = options;
		self.answer = option;
	}
	return self;
}

- (void)configureQuestionInView:(UIView *)view title:(UILabel *)title buttons:(NSArray *)buttons {
	[title setText:self.title];
	for (int i = 0; i < 4; i++) {
		[buttons[i] setTitle:[NSString stringWithFormat:@"%@", self.options[i]] forState:UIControlStateNormal];
		[buttons[i] addTarget:self action:@selector(buttonPressed:) forControlEvents:UIControlEventTouchUpInside];
		if (i == self.answer) [buttons[i] setTag:11610];
	}
}

- (void)finishQuestionInView:(UIView *)view title:(UILabel *)title buttons:(NSArray *)buttons {
	for (int i = 0; i < 4; i++) {
		[buttons[i] removeTarget:nil action:nil forControlEvents:UIControlEventAllEvents];
	}
}

- (void)buttonPressed:(UIButton *)button {
	if (self.delegate) [self.delegate questionAnswered:button.tag == 11610];
}

@end
