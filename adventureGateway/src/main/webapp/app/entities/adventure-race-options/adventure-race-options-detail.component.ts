import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdventureRaceOptions } from 'app/shared/model/adventure-race-options.model';

@Component({
  selector: 'jhi-adventure-race-options-detail',
  templateUrl: './adventure-race-options-detail.component.html'
})
export class AdventureRaceOptionsDetailComponent implements OnInit {
  adventureRaceOptions: IAdventureRaceOptions;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ adventureRaceOptions }) => {
      this.adventureRaceOptions = adventureRaceOptions;
    });
  }

  previousState() {
    window.history.back();
  }
}
