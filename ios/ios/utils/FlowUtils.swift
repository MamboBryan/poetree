//
//  FlowUtils.swift
//  ios
//
//  Created by MamboBryan on 22/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

fun <T> Flow<T>.wrap(): CommonFlow<T> = CommonFlow(this)
