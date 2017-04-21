//
//  TileEndViewController.m
//  AppBowl2017
//
//  Created by Ethan Tillison on 3/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import "TileEndViewController.h"

@interface TileEndViewController ()

@end

@implementation TileEndViewController

@synthesize time, moves, timeString, movesString;

- (void)viewDidLoad {
    [super viewDidLoad];
	
	// Set the text of the labels
	[time setText:timeString];
	[moves setText:movesString];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
