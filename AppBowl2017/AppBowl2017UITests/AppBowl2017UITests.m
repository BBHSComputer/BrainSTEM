//
//  AppBowl2017UITests.m
//  AppBowl2017UITests
//
//  Created by Ethan Tillison on 1/3/17.
//  Copyright © 2017 Blind Brook Computer Club. All rights reserved.
//

#import <XCTest/XCTest.h>

@interface AppBowl2017UITests : XCTestCase

@end

@implementation AppBowl2017UITests

- (void)setUp {
    [super setUp];
    
    // Put setup code here. This method is called before the invocation of each test method in the class.
    
    // In UI tests it is usually best to stop immediately when a failure occurs.
    self.continueAfterFailure = NO;
    // UI tests must launch the application that they test. Doing this in setup will make sure it happens for each test method.
    [[[XCUIApplication alloc] init] launch];
    
    // In UI tests it’s important to set the initial state - such as interface orientation - required for your tests before they run. The setUp method is a good place to do this.
}

- (void)tearDown {
    // Put teardown code here. This method is called after the invocation of each test method in the class.
    [super tearDown];
}

- (void)testExample {
    // Use recording to get started writing UI tests.
    // Use XCTAssert and related functions to verify your tests produce the correct results.
}

@end
