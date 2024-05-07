//
//  ContentView.swift
//  iosApp
//
//  Created by Andrei Yablonski on 7.05.24.
//

import SwiftUI

import shared

struct ContentView: View {
    var body: some View {
            Text(Greeting().greet())
            .padding()
        }
}

#Preview {
    ContentView()
}
