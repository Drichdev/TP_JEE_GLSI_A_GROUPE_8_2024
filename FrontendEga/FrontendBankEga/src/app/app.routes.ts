import { Routes } from '@angular/router';
import { ClientComponent } from './client/client.component';
import { CompteComponent } from './compte/compte.component';
import { HomeComponent } from './home/home.component';
import { AdminComponent } from './admin/admin.component';

export const routes: Routes = [
    {
        path : "", component : HomeComponent
    },
    {
        path : "client", component : ClientComponent
    },
    {
        path : "compte", component : CompteComponent
    },
    {
        path : "admin", component : AdminComponent
    }
];
