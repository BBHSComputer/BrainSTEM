//
//  YesRule.m
//  AppBowl2017
//
//  Created by Ethan Tillison on 5/4/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import "Rule.h"

@implementation Rule

/// Is the first value allowed next to the second value?
- (BOOL)isNewTile:(NSNumber *)tile allowedNextTo:(NSNumber *)adjacent inTileArray:(NSArray *)tiles {
	return YES;
}

@end
