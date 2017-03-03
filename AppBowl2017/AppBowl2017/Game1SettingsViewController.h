//
//  Game1SettingsViewController.h
//  AppBowl2017
//
//  Created by Ethan Tillison on 2/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Game1ViewController.h"

@interface Game1SettingsViewController : UIViewController

@property (strong, nonatomic) IBOutlet UILabel *stepperValue;
@property (strong, nonatomic) IBOutlet UIStepper *stepper;

- (IBAction)stepperChanged:(UIStepper *)sender;

@end
