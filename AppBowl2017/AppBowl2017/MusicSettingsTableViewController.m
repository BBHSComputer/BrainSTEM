//
//  MusicSettingsTableViewController.m
//  AppBowl2017
//
//  Created by Ethan Tillison on 26/4/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import "MusicSettingsTableViewController.h"
#import "MusicSettingsTableViewCell.h"
#import "MusicViewController.h"

@interface MusicSettingsTableViewController ()

@end

@implementation MusicSettingsTableViewController

- (void)viewDidLoad {
	[super viewDidLoad];
}

- (void)didReceiveMemoryWarning {
	[super didReceiveMemoryWarning];
	// Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
	return 1;
}

// Number of rows = number of songs
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
	return 3;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
	MusicSettingsTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"Song" forIndexPath:indexPath];
	
	switch (indexPath.row) {
		case 0:
			[cell.name setText:@"The Star Spangled Banner"];
			[cell.composer setText:@"Francis Scott Key"];
			[cell.copyright setText:@"Adapted from Bryce Poindexter"];
			break;
		case 1:
			[cell.name setText:@"Take Me Out to the Ball Game"];
			[cell.composer setText:@"Jack Norworth and Albert Von Tilzer"];
			[cell.copyright setText:nil];
			break;
		case 2:
			[cell.name setText:@"Für Elise"];
			[cell.composer setText:@"Ludwig van Beethoven"];
			[cell.copyright setText:nil];
			break;
	}
	
	return cell;
}

- (NSString *)tableView:(UITableView *)tableView titleForFooterInSection:(NSInteger)section {
	return @"All music is adapted from sources licensed under the Creative Commons Attribution-Non Commercial-Share Alike 3.0 License (CC BY-NC-SA 3.0). See the about page for more details.";
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
	[self performSegueWithIdentifier:@"PlayGame" sender:[NSNumber numberWithInteger:indexPath.row]];
}

#pragma mark - Navigation

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
	if ([segue.identifier isEqualToString:@"PlayGame"]) {
		if ([sender isKindOfClass:[NSNumber class]]) [(MusicViewController *) [segue destinationViewController] setSong:[sender intValue]];
	}
}

@end
