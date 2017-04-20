//
//  Game1SettingsViewController.h
//  AppBowl2017
//
//  Created by Ethan Tillison on 2/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Photos/Photos.h>
#import <PhotosUI/PhotosUI.h>
#import "Game1ViewController.h"
#import "PhotoCollectionViewCell.h"

@interface Game1SettingsViewController : UIViewController <UICollectionViewDelegate, UICollectionViewDataSource>

@property (strong, nonatomic) IBOutlet UILabel *stepperValue; // The display for the value of the stepper
@property (strong, nonatomic) IBOutlet UIStepper *stepper; // The stepper for the number of images
@property (strong, nonatomic) IBOutlet UICollectionView *collection; // The collection view for the images

@property (strong, nonatomic) NSMutableArray *images; // An array of the selected images

- (IBAction)stepperChanged:(UIStepper *)sender;

- (IBAction)selectImages:(UIButton *)sender;

- (IBAction)unwindToSettings:(UIStoryboardSegue *)segue;

@end
