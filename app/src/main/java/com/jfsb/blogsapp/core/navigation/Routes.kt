package com.jfsb.blogsapp.core.navigation

sealed class Routes(val route: String) {
    object Dashboard : Routes("Dashboard")
}