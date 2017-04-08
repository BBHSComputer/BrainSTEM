//
//  AdjacencyRule.m
//  AppBowl2017
//
//  Created by Ethan Tillison on 5/4/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import "AdjacencyRule.h"

@implementation AdjacencyRule

@synthesize tile1, tile2;

- (instancetype)initWithTile:(NSNumber *)t1 and:(NSNumber *)t2 {
	self = [super init];
	if (self) {
		tile1 = MIN(t1, t2);
		tile2 = MAX(t1, t2);
		NSLog(@"%@ %@", t1, t2);
	}
	return self;
}

- (BOOL)isNewTile:(NSNumber *)tile allowedNextTo:(NSNumber *)adjacent inTileArray:(NSArray *)tiles {
	return !([tile isEqualToNumber:tile1] && [adjacent isEqualToNumber:tile2]) && !([tile isEqualToNumber:tile2] && [adjacent isEqualToNumber:tile1]);
}

- (BOOL)isEqual:(id)object {
	if (object == nil) return NO;
	if (self == object) return YES;
	if (![object isKindOfClass:[self class]]) return NO;
	AdjacencyRule *rule = (AdjacencyRule *)object;
	return [rule.tile1 isEqual:self.tile1] && [rule.tile2 isEqual:self.tile2];
}

@end
