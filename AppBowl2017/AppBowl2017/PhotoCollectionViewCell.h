//
//  PhotoCollectionViewCell.h
//  AppBowl2017
//
//  Created by Ethan Tillison on 22/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface PhotoCollectionViewCell : UICollectionViewCell

@property (strong, nonatomic) IBOutlet UIImageView *image;
@property (strong, nonatomic) IBOutlet UIImageView *checkbox;
@property (nonatomic) BOOL checked;

- (IBAction)select:(UIButton *)sender;

@end
