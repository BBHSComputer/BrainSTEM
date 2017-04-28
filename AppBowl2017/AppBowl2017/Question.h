//
//  Question.h
//  AppBowl2017
//
//  Created by Ethan Tillison on 27/4/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol QuestionDelegate <NSObject>

@required
- (void)questionAnswered:(BOOL)correct;

@end

@interface Question : NSObject

@property (strong, nonatomic) id<QuestionDelegate> delegate;

- (void)configureQuestionInView:(UIView *)view title:(UILabel *)title buttons:(NSArray *)buttons;

- (void)finishQuestionInView:(UIView *)view title:(UILabel *)title buttons:(NSArray *)buttons;

@end
