//
//  Game2SettingsViewController.m
//  AppBowl2017
//
//  Created by Ethan Tillison on 28/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import "StackSettingsViewController.h"

@interface StackSettingsViewController ()

@end

@implementation StackSettingsViewController

- (void)viewDidLoad {
    [super viewDidLoad];
	[self.desc setText:@"Drop tiles to fill the grid!\nDiscover the rules:\ntiles that break rules explode and destroy nearby tiles."];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
