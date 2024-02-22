import { BytesToTextPipe } from './bytes-to-text.pipe';

describe('BytesToTextPipe', () => {
  it('create an instance', () => {
    const pipe = new BytesToTextPipe();
    expect(pipe).toBeTruthy();
  });
});
