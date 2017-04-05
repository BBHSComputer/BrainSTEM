//
//  PhotoCollectionViewCell.m
//  AppBowl2017
//
//  Created by Ethan Tillison on 22/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import "PhotoCollectionViewCell.h"

@implementation PhotoCollectionViewCell

@synthesize image, checkbox, selected;

- (IBAction)select:(UIButton *)sender {
	selected = !selected;
	[checkbox setImage:[UIImage imageNamed:(selected ? @"A" : @"B")]];
}

@end
