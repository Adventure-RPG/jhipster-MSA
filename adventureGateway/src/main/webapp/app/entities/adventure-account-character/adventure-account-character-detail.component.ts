import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdventureAccountCharacter } from 'app/shared/model/adventure-account-character.model';

@Component({
  selector: 'jhi-adventure-account-character-detail',
  templateUrl: './adventure-account-character-detail.component.html'
})
export class AdventureAccountCharacterDetailComponent implements OnInit {
  adventureAccountCharacter: IAdventureAccountCharacter;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ adventureAccountCharacter }) => {
      this.adventureAccountCharacter = adventureAccountCharacter;
    });
  }

  previousState() {
    window.history.back();
  }
}
