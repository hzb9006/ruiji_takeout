package ruiji_takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.mapper.AddressBookMapper;
import generator.pojo.AddressBook;
import org.springframework.stereotype.Service;
import ruiji_takeout.service.AddressBookService;

/**
* @author 大饼干
* @description 针对表【address_book(地址管理)】的数据库操作Service实现
* @createDate 2022-10-13 11:29:15
*/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
    implements AddressBookService {

}




