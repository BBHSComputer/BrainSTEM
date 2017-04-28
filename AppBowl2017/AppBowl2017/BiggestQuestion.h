//
//  BiggestQuestion.h
//  AppBowl2017
//
//  Created by Ethan Tillison on 28/4/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import "Question.h"

@interface BiggestQuestion : Question

@property (strong, nonatomic) NSString *title;
@property (strong, nonatomic) NSArray *options;
@property (strong, nonatomic) UIButton *correctButton;

- (instancetype)initWithTitle:(NSString *)title options:(NSArray *)options;


@end
