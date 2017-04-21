//
//  GameListTableViewController.h
//  AppBowl2017
//
//  Created by Ethan Tillison on 21/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GameListTableViewCell.h"
#import "TileSettingsViewController.h"

@interface GameListTableViewController : UITableViewController

- (IBAction)unwindToHome:(UIStoryboardSegue *)segue;

@end
