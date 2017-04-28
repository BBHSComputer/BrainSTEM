//
//  SummationEndViewController.m
//  AppBowl2017
//
//  Created by Ethan Tillison on 28/4/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import "SummationEndViewController.h"

@interface SummationEndViewController ()

@end

@implementation SummationEndViewController

@synthesize scoreString;

- (void)viewDidLoad {
    [super viewDidLoad];
	
	[self.score setText:scoreString];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
