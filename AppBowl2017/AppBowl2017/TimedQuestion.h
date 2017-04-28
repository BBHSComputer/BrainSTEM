//
//  TimedQuestion.h
//  AppBowl2017
//
//  Created by Ethan Tillison on 28/4/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import "Question.h"

@interface TimedQuestion : Question

@property (strong, nonatomic) NSString *title;
@property (strong, nonatomic) NSArray *options;
@property (nonatomic) int answer;
@property (nonatomic) NSTimeInterval time;
@property (nonatomic) BOOL timeElapsed;

- (instancetype)initWithTitle:(NSString *)title options:(NSArray *)options correctOption:(int)option time:(NSTimeInterval)time;

@end
