import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAdventureAccountCharacter, AdventureAccountCharacter } from 'app/shared/model/adventure-account-character.model';
import { AdventureAccountCharacterService } from './adventure-account-character.service';
import { IAdventureInventoryChar } from 'app/shared/model/adventure-inventory-char.model';
import { AdventureInventoryCharService } from 'app/entities/adventure-inventory-char';
import { IAdventureCharacteristic } from 'app/shared/model/adventure-characteristic.model';
import { AdventureCharacteristicService } from 'app/entities/adventure-characteristic';
import { IAdventureRace } from 'app/shared/model/adventure-race.model';
import { AdventureRaceService } from 'app/entities/adventure-race';
import { IAdventureSkill } from 'app/shared/model/adventure-skill.model';
import { AdventureSkillService } from 'app/entities/adventure-skill';

@Component({
  selector: 'jhi-adventure-account-character-update',
  templateUrl: './adventure-account-character-update.component.html'
})
export class AdventureAccountCharacterUpdateComponent implements OnInit {
  adventureAccountCharacter: IAdventureAccountCharacter;
  isSaving: boolean;

  adventureinventorychars: IAdventureInventoryChar[];

  adventurecharacteristics: IAdventureCharacteristic[];

  adventureraces: IAdventureRace[];

  adventureskills: IAdventureSkill[];

  editForm = this.fb.group({
    id: [],
    nickname: [null, [Validators.required]],
    gender: [null, [Validators.required]],
    adventureInventoryCharId: [],
    adventureCharacteristicId: [],
    adventureRaceId: [],
    adventureSkills: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected adventureAccountCharacterService: AdventureAccountCharacterService,
    protected adventureInventoryCharService: AdventureInventoryCharService,
    protected adventureCharacteristicService: AdventureCharacteristicService,
    protected adventureRaceService: AdventureRaceService,
    protected adventureSkillService: AdventureSkillService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ adventureAccountCharacter }) => {
      this.updateForm(adventureAccountCharacter);
      this.adventureAccountCharacter = adventureAccountCharacter;
    });
    this.adventureInventoryCharService
      .query({ filter: 'adventureaccountcharacter-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IAdventureInventoryChar[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAdventureInventoryChar[]>) => response.body)
      )
      .subscribe(
        (res: IAdventureInventoryChar[]) => {
          if (!this.adventureAccountCharacter.adventureInventoryCharId) {
            this.adventureinventorychars = res;
          } else {
            this.adventureInventoryCharService
              .find(this.adventureAccountCharacter.adventureInventoryCharId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IAdventureInventoryChar>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IAdventureInventoryChar>) => subResponse.body)
              )
              .subscribe(
                (subRes: IAdventureInventoryChar) => (this.adventureinventorychars = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.adventureCharacteristicService
      .query({ filter: 'adventureaccountcharacter-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IAdventureCharacteristic[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAdventureCharacteristic[]>) => response.body)
      )
      .subscribe(
        (res: IAdventureCharacteristic[]) => {
          if (!this.adventureAccountCharacter.adventureCharacteristicId) {
            this.adventurecharacteristics = res;
          } else {
            this.adventureCharacteristicService
              .find(this.adventureAccountCharacter.adventureCharacteristicId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IAdventureCharacteristic>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IAdventureCharacteristic>) => subResponse.body)
              )
              .subscribe(
                (subRes: IAdventureCharacteristic) => (this.adventurecharacteristics = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.adventureRaceService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAdventureRace[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAdventureRace[]>) => response.body)
      )
      .subscribe((res: IAdventureRace[]) => (this.adventureraces = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.adventureSkillService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAdventureSkill[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAdventureSkill[]>) => response.body)
      )
      .subscribe((res: IAdventureSkill[]) => (this.adventureskills = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(adventureAccountCharacter: IAdventureAccountCharacter) {
    this.editForm.patchValue({
      id: adventureAccountCharacter.id,
      nickname: adventureAccountCharacter.nickname,
      gender: adventureAccountCharacter.gender,
      adventureInventoryCharId: adventureAccountCharacter.adventureInventoryCharId,
      adventureCharacteristicId: adventureAccountCharacter.adventureCharacteristicId,
      adventureRaceId: adventureAccountCharacter.adventureRaceId,
      adventureSkills: adventureAccountCharacter.adventureSkills
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const adventureAccountCharacter = this.createFromForm();
    if (adventureAccountCharacter.id !== undefined) {
      this.subscribeToSaveResponse(this.adventureAccountCharacterService.update(adventureAccountCharacter));
    } else {
      this.subscribeToSaveResponse(this.adventureAccountCharacterService.create(adventureAccountCharacter));
    }
  }

  private createFromForm(): IAdventureAccountCharacter {
    const entity = {
      ...new AdventureAccountCharacter(),
      id: this.editForm.get(['id']).value,
      nickname: this.editForm.get(['nickname']).value,
      gender: this.editForm.get(['gender']).value,
      adventureInventoryCharId: this.editForm.get(['adventureInventoryCharId']).value,
      adventureCharacteristicId: this.editForm.get(['adventureCharacteristicId']).value,
      adventureRaceId: this.editForm.get(['adventureRaceId']).value,
      adventureSkills: this.editForm.get(['adventureSkills']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureAccountCharacter>>) {
    result.subscribe(
      (res: HttpResponse<IAdventureAccountCharacter>) => this.onSaveSuccess(),
      (res: HttpErrorResponse) => this.onSaveError()
    );
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAdventureInventoryCharById(index: number, item: IAdventureInventoryChar) {
    return item.id;
  }

  trackAdventureCharacteristicById(index: number, item: IAdventureCharacteristic) {
    return item.id;
  }

  trackAdventureRaceById(index: number, item: IAdventureRace) {
    return item.id;
  }

  trackAdventureSkillById(index: number, item: IAdventureSkill) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
