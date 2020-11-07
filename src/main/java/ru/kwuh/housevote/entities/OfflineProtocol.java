package ru.kwuh.housevote.entities;

import com.google.common.hash.HashCode;
import lombok.Data;

import java.util.List;

@Data
public class OfflineProtocol {
    byte[] protocolFile;
    HashCode protocolHash;
    List<OfflineVoter> offlineVoters;
}
