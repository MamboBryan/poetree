//
//  AccountScreen.swift
//  ios
//
//  Created by MamboBryan on 22/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct AccountScreen: View {
    
    var isSettingUp : Bool
    
    var body: some View {
        Text("Account")
    }
}

struct AccountScreen_Previews: PreviewProvider {
    static var previews: some View {
        AccountScreen(isSettingUp: false)
    }
}
