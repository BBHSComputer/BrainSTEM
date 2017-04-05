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
		tile1 = t1;
		tile2 = t2;
		NSLog(@"%@ %@", t1, t2);
	}
	return self;
}

- (BOOL)isNewTile:(NSNumber *)tile allowedNextTo:(NSNumber *)adjacent inTileArray:(NSArray *)tiles {
	NSLog(@"A %@", [tile isEqualToNumber:tile1] ? @"YES" : @"NO");
	NSLog(@"B %@", [adjacent isEqualToNumber:tile2] ? @"YES" : @"NO");
	NSLog(@"C %@", [tile isEqualToNumber:tile2] ? @"YES" : @"NO");
	NSLog(@"D %@", [adjacent isEqualToNumber:tile1] ? @"YES" : @"NO");
	return !([tile isEqualToNumber:tile1] && [adjacent isEqualToNumber:tile2]) && !([tile isEqualToNumber:tile2] && [adjacent isEqualToNumber:tile1]);
}

@end
