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

NSUInteger previousValue;

@synthesize stepperValue, stepper, collection, images;

#pragma mark - Interface Builder Actions

/// Called when the value of the stepper is changed
- (IBAction)stepperChanged:(UIStepper *)sender {
	// Update the label
	[stepperValue setText:[NSString stringWithFormat:@"%i", (int) sender.value]];
	
	// Update the collection view by adding or removing cells
	if (previousValue > sender.value) {
		[collection deleteItemsAtIndexPaths:@[[NSIndexPath indexPathForItem:previousValue - 1 inSection:0]]];
	} else if (previousValue < sender.value) {
		[collection insertItemsAtIndexPaths:@[[NSIndexPath indexPathForItem:sender.value - 1 inSection:0]]];
	}
	previousValue = sender.value;
}

/// Select Images (TODO)
- (IBAction)selectImages:(UIButton *)sender {
	// PHPhotoLibrary *lib = [PHPhotoLibrary sharedPhotoLibrary];
	PHFetchResult *list = [PHCollectionList fetchCollectionListsWithType:PHCollectionListTypeMomentList subtype:PHCollectionListSubtypeAny options:nil];
	
	for (int i = 0; i < [list count]; i++) {
		PHCollectionList *collectionList = [list objectAtIndex:i];
		PHFetchResult *assetList = [PHAssetCollection fetchMomentsInMomentList:collectionList options:nil];
		for (int n = 0; n < assetList.count; n++) {
			PHAssetCollection *assetCollection = [assetList objectAtIndex:n];
			PHAsset *asset = [[PHAsset fetchAssetsInAssetCollection:assetCollection options:nil] objectAtIndex:0];
			[[PHImageManager defaultManager] requestImageForAsset:asset targetSize:CGSizeMake(128, 128) contentMode:PHImageContentModeAspectFit options:nil resultHandler:^(UIImage * _Nullable result, NSDictionary * _Nullable info) {
				UIImageView *img = [[UIImageView alloc] initWithImage:result];
				[img setFrame:CGRectMake(0, 0, 128, 128)];
				[self.view addSubview:img];
			}];
		}
	}
	
}

- (IBAction)unwindToSettings:(UIStoryboardSegue *)segue {}

#pragma mark - ViewController Methods

- (void)viewDidLoad {
    [super viewDidLoad];
	
	// Instantiate the default images
	self.images = [[NSMutableArray alloc] initWithObjects:[UIImage imageNamed:@"A"], [UIImage imageNamed:@"B"], [UIImage imageNamed:@"C"], [UIImage imageNamed:@"D"], [UIImage imageNamed:@"E"], [UIImage imageNamed:@"F"], [UIImage imageNamed:@"G"], [UIImage imageNamed:@"H"], [UIImage imageNamed:@"I"], [UIImage imageNamed:@"J"], [UIImage imageNamed:@"K"], [UIImage imageNamed:@"L"], [UIImage imageNamed:@"M"], [UIImage imageNamed:@"N"], [UIImage imageNamed:@"O"], [UIImage imageNamed:@"P"], nil];
	
	// Set the back button to <Home instead of <BrainSTEM
	self.navigationController.navigationBar.topItem.backBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"Home" style:UIBarButtonItemStylePlain target:nil action:nil];
	[self.navigationController.navigationBar.topItem.backBarButtonItem setTitleTextAttributes:@{[UIFont fontWithName:@"Montserrat-Regular" size:17.0]: NSFontAttributeName} forState:UIControlStateNormal];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Collection View Methods

- (NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView {
	return 1;
}

- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
	return (NSInteger) [stepper value];
}

- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
	PhotoCollectionViewCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"PhotoCell" forIndexPath:indexPath];
	
	[cell.image setImage:[self.images objectAtIndex:indexPath.row]];
	
	return cell;
}

#pragma mark - Navigation

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
	if ([segue.identifier isEqualToString:@"PlayGame"]) { // Will be called then the play button is pressed
		Game1ViewController *vc = (Game1ViewController *) [segue destinationViewController];
		[vc layoutImages:stepper.value];
		vc.images = [self.images mutableCopy];
		[vc reset];
	}
}

@end
