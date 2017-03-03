//
//  Game1SettingsViewController.m
//  AppBowl2017
//
//  Created by Ethan Tillison on 2/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import "Game1SettingsViewController.h"

@interface Game1SettingsViewController ()

@end

@implementation Game1SettingsViewController

@synthesize stepperValue;
@synthesize stepper;

- (IBAction)stepperChanged:(UIStepper *)sender {
	[stepperValue setText:[NSString stringWithFormat:@"%i", (int) sender.value]];
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Navigation

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
	if ([segue.identifier isEqualToString:@"PlayGame"]) {
		Game1ViewController *vc = (Game1ViewController *) [segue destinationViewController];
		[vc layoutImages:stepper.value];
		vc.images = [[NSMutableArray alloc] initWithObjects:[UIImage imageNamed:@"A"], [UIImage imageNamed:@"B"], [UIImage imageNamed:@"C"], [UIImage imageNamed:@"D"], [UIImage imageNamed:@"E"], nil];
	}
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}

@end
