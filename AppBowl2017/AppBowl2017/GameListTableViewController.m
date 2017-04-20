//
//  GameListTableViewController.m
//  AppBowl2017
//
//  Created by Ethan Tillison on 21/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import "GameListTableViewController.h"

@interface GameListTableViewController ()

@end

@implementation GameListTableViewController

- (void)viewDidLoad {
    [super viewDidLoad];
	
	[(UILabel *) self.navigationItem.titleView setFont:[UIFont fontWithName:@"PlayfairDisplay-Regular" size:24]];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 4;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    GameListTableViewCell *cell = (GameListTableViewCell *) [tableView dequeueReusableCellWithIdentifier:@"GameCell" forIndexPath:indexPath];
	
	// Configure the table cells
	[cell.name setText:@[@"Concentration", @"Neostriatum", @"DDR Clone", @"Help what's the last game"][indexPath.row]];
	[cell.foci setText:@[@"Memory", @"Perception, Memory", @"Reflexes, Dexterity", @"Memory"][indexPath.row]];
	[cell.image setImage:[UIImage imageNamed:@"AppIcon"]];
	
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
	[self performSegueWithIdentifier:[NSString stringWithFormat:@"%li", (long) indexPath.row] sender:tableView];
}


#pragma mark - Navigation

/*
// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

- (IBAction)unwindToHome:(UIStoryboardSegue *)segue {}

@end
