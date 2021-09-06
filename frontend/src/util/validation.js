import { extend } from 'vee-validate';
import {
  required, 
  max,
  size,
  image,
  excluded,
  is_not,
  numeric,
  required_if,
  min,
} from 'vee-validate/dist/rules';

extend('required', {
  ...required,
  message: '필수 입력항목입니다.'
})
extend('max', {
  ...max,
  params: ['length'],
  message: '최대 {length}자 입니다.'
})
extend('size',{
  ...size,
  params: ['size'],
  message: '최대 1MB 첨부 가능합니다.'
}) 
extend('image', {
  ...image,
  message: '이미지파일만 가능합니다.'
})
extend('excluded', {
  ...excluded,
  message: '공연시간을 등록해주세요.'
})
extend('numeric', {
  ...numeric,
  message: '숫자로 적어주세요.'
})
extend('is_not', {
  ...is_not,
  params: ['value'],
  validate(value) {
    console.log(value)
  },
  message: '공연을 등록해주세요.'
})
extend('required_if', {
  ...required_if,
  params: ['target', '...values'],
  validate(target, values) {
    console.log(target, values)
    if (values.contains('홍보')||values.contains('공연')) { 
      return '{target} 용일 경우 값을 넣어주세요'
    }
    return true
  },
})
extend('min', {
  ...min,
  params: [length],
  validate(value) {
    if (value == undefined) {
      return '공연을 등록해주세요.'
    } else {
      return true
    }
  },
  message: '공연을 등록해주세요.'
})