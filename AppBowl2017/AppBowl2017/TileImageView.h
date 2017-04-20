//
//  TileImageView.h
//  AppBowl2017
//
//  Created by Ethan Tillison on 2/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef enum { // Tile's state; states ending in -ing are animating states
	FlippingStateUnflipped,
	FlippingStateFlipping,
	FlippingStateFlipped,
	FlippingStateUnflipping
} FlippingState;

@interface TileImageView : UIImageView

@property (nonatomic) int imageId; // The index of the image
@property (nonatomic) FlippingState flipped; // The tile's state
@property (nonatomic) BOOL matched; // Whether or not this tile's match has been found
@property (nonatomic) int x, y; // The tile's coordinates

@end
