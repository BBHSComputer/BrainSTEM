//
//  Game1EndViewController.m
//  AppBowl2017
//
//  Created by Ethan Tillison on 3/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import "Game1EndViewController.h"

@interface Game1EndViewController ()

@end

@implementation Game1EndViewController

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
