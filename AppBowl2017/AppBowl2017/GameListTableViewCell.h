//
//  GameListTableViewCell.h
//  AppBowl2017
//
//  Created by Ethan Tillison on 21/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface GameListTableViewCell : UITableViewCell

@property (strong, nonatomic) IBOutlet UIImageView *image;
@property (strong, nonatomic) IBOutlet UILabel *name, *foci;

@end
