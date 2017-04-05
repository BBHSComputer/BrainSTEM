//
//  AdjacencyRule.h
//  AppBowl2017
//
//  Created by Ethan Tillison on 5/4/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import "Rule.h"

@interface AdjacencyRule : Rule

@property (readonly, strong, nonatomic) NSNumber *tile1, *tile2;

- (instancetype)initWithTile:(NSNumber *)t1 and:(NSNumber *)t2;

@end
