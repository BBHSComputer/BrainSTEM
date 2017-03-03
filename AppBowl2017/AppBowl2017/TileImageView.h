//
//  TileImageView.h
//  AppBowl2017
//
//  Created by Ethan Tillison on 2/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TileImageView : UIImageView

@property (nonatomic) int imageId;
@property (nonatomic) BOOL flipped;
@property (nonatomic) BOOL matched;
@property (nonatomic) int x, y;

@end
