//
//  NetworkMonitor.swift
//  ios
//
//  Created by MamboBryan on 03/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Network
import common

extension NWInterface.InterfaceType: CaseIterable {
    public static var allCases: [NWInterface.InterfaceType] = [
        .other,
        .wifi,
        .cellular,
        .loopback,
        .wiredEthernet
    ]
}

final class NetworkMonitor {
    
    static let shared = NetworkMonitor()
    
    private let queue = DispatchQueue(label: "NetworkMonitor")
    private let monitor : NWPathMonitor
    
    private(set) var isConnected = false
    private(set) var isExpensive = false
    private(set) var currentConnectionType: NWInterface.InterfaceType?
    
    private init() {
        monitor = NWPathMonitor()
    }
    
    func start(){
        monitor.pathUpdateHandler = { [weak self] path in
            self?.isConnected = path.status != .unsatisfied
            self?.isExpensive = path.isExpensive
            self?.currentConnectionType = NWInterface.InterfaceType.allCases.filter {
                path.usesInterfaceType($0)
            }.first
            
            UserPreferences().updateNetworkConnection(isConnected: path.status != .unsatisfied)
            
        }
        monitor.start(queue: queue)
    }
    
    func stop(){
        monitor.cancel()
    }
    
    
}
