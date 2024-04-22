import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StavkaPorudzbinaComponent } from './stavka-porudzbina.component';

describe('StavkaPorudzbinaComponent', () => {
  let component: StavkaPorudzbinaComponent;
  let fixture: ComponentFixture<StavkaPorudzbinaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StavkaPorudzbinaComponent]
    });
    fixture = TestBed.createComponent(StavkaPorudzbinaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
