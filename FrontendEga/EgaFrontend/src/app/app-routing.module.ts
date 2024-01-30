import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClientComponent } from './client/client.component';
import { HomeComponent } from './home/home.component';
import { CompteComponent } from './compte/compte.component';

const routes: Routes = [
  {path: "/", component : HomeComponent},
  { path : "/client", component : ClientComponent },
  {path: "/compte", component : CompteComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
