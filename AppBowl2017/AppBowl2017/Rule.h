//
//  YesRule.h
//  AppBowl2017
//
//  Created by Ethan Tillison on 5/4/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import <Foundation/Foundation.h>

/// A rule. Rules define whether or not two tiles are allowed to be adjacent
@interface Rule : NSObject

- (BOOL)isNewTile:(NSNumber *)tile allowedNextTo:(NSNumber *)adjacent inTileArray:(NSArray *)tiles;

@end
